package br.com.blog.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.blog.model.Privilegio;

public interface PrivilegioRepository extends CrudRepository<Privilegio, Long> {

	Privilegio findByNome(String nome);

}
