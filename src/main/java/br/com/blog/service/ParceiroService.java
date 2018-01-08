package br.com.blog.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.blog.model.Parceiro;
import br.com.blog.repository.ParceiroRepository;

@Service
public class ParceiroService {
		
	@Autowired
	private ParceiroRepository repository;
	
	@Cacheable("parceiroCache")
	public Page<Parceiro> listarTodos(Pageable pageable){
		return repository.listarTodos(pageable);
	}

	
	@CacheEvict(value="parceiroCache", allEntries=true)
	public Parceiro salva(Parceiro parceiro) {
		return repository.save(parceiro);
	}
	
	@CacheEvict(value="parceiroCache", allEntries=true)
	public void deletar(Long id) {
		repository.delete(id);
	}
	
	@Cacheable("parceiroCache")
	public List<Parceiro> listarTodos(){
		return repository.listAll();
	}

}
