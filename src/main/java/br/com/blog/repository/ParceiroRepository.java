package br.com.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.blog.model.Parceiro;

public interface ParceiroRepository extends CrudRepository<Parceiro, Long> {
	
	@Query("select p from Parceiro p order by p.id desc")
	List<Parceiro> listAll();
	
	@Query("select p from Parceiro p order by p.id desc")
	Page<Parceiro> listarTodos(Pageable pageable);
		
	
}
