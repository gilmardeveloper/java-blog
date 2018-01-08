package br.com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.blog.model.Assinante;

public interface AssinanteRepository extends CrudRepository<Assinante, Long> {
	
	@Query("select a from Assinante a order by a.dataAssinatura desc")
	List<Assinante> listAll();

	Assinante findByEmail(String email);

}
