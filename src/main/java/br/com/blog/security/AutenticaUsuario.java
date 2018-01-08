package br.com.blog.security;

import org.springframework.context.ApplicationEvent;

import br.com.blog.model.Usuario;

public class AutenticaUsuario extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;

	public AutenticaUsuario(Usuario usuario) {
		super(usuario);
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	
}
