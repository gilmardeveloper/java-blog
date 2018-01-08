package br.com.blog.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.blog.model.Autorizacao;

public interface AutorizacaoRepository extends CrudRepository<Autorizacao, Long>{

	Autorizacao findByNome(String nome);

}
