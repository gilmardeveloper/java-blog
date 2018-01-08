package br.com.blog.security;

import br.com.blog.model.Usuario;

public interface AutenticaToken {

	void criarVerificacao(Usuario usuario);
	void habilitar(Usuario usuario);
	
}
