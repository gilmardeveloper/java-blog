package br.com.blog.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.blog.model.Usuario;
import br.com.blog.model.UsuarioToken;

public interface VerificacaoTokenRepository extends CrudRepository<UsuarioToken, Long>{
	
	UsuarioToken findByToken(String token);
    UsuarioToken findByUsuario(Usuario usuario);

}
