package br.com.blog.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.blog.model.Usuario;
import br.com.blog.model.UsuarioToken;
import br.com.blog.repository.AutorizacaoRepository;
import br.com.blog.repository.UsuarioRepository;
import br.com.blog.repository.VerificacaoTokenRepository;
import br.com.blog.security.AutenticaRegistroUsuario;
import br.com.blog.security.AutenticaUsuario;
import br.com.blog.security.PasswordCrypt;

@Service
public class AutenticadorService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordCrypt passwordCrypt;
	
	@Autowired
	private AutorizacaoRepository autorizacaoRepository;
	
	@Autowired
	private VerificacaoTokenRepository tokenRepository;
	
	@Autowired
	private ApplicationEventPublisher eventPublish;
	
	@Autowired
	private AutenticaRegistroUsuario autenticaRegistro;
	
	public void registrarVerificacao(Usuario usuario) {

		usuario.setAutorizacoes(Arrays.asList(autorizacaoRepository.findByNome("ROLE_USER")));
		usuario.setSenha(passwordCrypt.encode(usuario.getSenha()));
		Usuario usuarioRegistrado = usuarioRepository.save(usuario);
		eventPublish.publishEvent(new AutenticaUsuario(usuarioRegistrado));
		
	}
	
	public void registrarNovaVerificacao(String token) {
		Usuario usuario = tokenRepository.findByToken(token).getUsuario();
		eventPublish.publishEvent(new AutenticaUsuario(usuario));
	}
	
	public boolean validarVerificacao(String token) throws Exception {
		
		UsuarioToken usuarioToken = tokenRepository.findByToken(token);
		
		if(usuarioToken == null) {
			throw new Exception("Token inválido");
			
		}else if(usuarioToken.expirou()) {
			return false;
			
		}else {
			autenticaRegistro.habilitar(usuarioToken.getUsuario());
			return true;
		}
		
		
	}

}
