package br.com.blog.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.blog.model.Usuario;
import br.com.blog.model.UsuarioToken;
import br.com.blog.repository.UsuarioRepository;
import br.com.blog.repository.VerificacaoTokenRepository;
import br.com.blog.service.EmailService;

@Component
public class AutenticaRegistroUsuario implements AutenticaToken {

	@Autowired
	private VerificacaoTokenRepository tokenRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void criarVerificacao(Usuario usuario) {
		String token = gerarToken();
		tokenRepository.save(new UsuarioToken(usuario, token));
		emailService.enviar(usuario, token);
	}

	public String gerarToken() {
		return UUID.randomUUID().toString();
	}

	@Override
	public void habilitar(Usuario usuario) {
		usuario.setHabilitado(true);
		usuarioRepository.save(usuario);		
	}

}
