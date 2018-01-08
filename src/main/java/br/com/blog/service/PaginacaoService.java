package br.com.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.blog.model.Categoria;
import br.com.blog.model.Post;
import br.com.blog.repository.PaginacaoRepository;

@Service
public class PaginacaoService {
	
	private final static Integer MINIMO_PAGES = 0;
	private final static Integer MAXIMO_PAGES = 5;
	
	@Autowired
	private PaginacaoRepository repository;
	
	@Cacheable("postCache")
	public Page<Post> listarTodos(Pageable pageable){
		return repository.listarTodos(pageable);
	}

	public Page<Post> buscarPor(Categoria categoria) {
		return repository.findByCategoria(categoria, new PageRequest(MINIMO_PAGES, MAXIMO_PAGES));
	}
	
	public Page<Post> pesquisar(String assunto) {
		return repository.pesquisar(assunto, new PageRequest(MINIMO_PAGES, MAXIMO_PAGES));
	}

}
