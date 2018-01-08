package br.com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.blog.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	Usuario findByEmail(String email);

	@Query("select u from Usuario u")
	List<Usuario> listAll();

}
