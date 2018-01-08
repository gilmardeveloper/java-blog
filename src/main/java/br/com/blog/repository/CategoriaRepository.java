package br.com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.blog.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
	
	@Query("select c from Categoria c")
	List<Categoria> listAll();
	
	Categoria findByTag(String tag);
	
}
