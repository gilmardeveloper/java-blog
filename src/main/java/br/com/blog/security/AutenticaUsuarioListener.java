package br.com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import br.com.blog.model.Usuario;

@Component
public class AutenticaUsuarioListener implements ApplicationListener<AutenticaUsuario>{
	
	@Autowired
	private AutenticaRegistroUsuario autenticador;
	
	@Override
	public void onApplicationEvent(AutenticaUsuario autentica) {
		
		Usuario usuario = autentica.getUsuario();
        autenticador.criarVerificacao(usuario);
		
	}

	
}
