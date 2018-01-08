package br.com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.blog.model.Categoria;
import br.com.blog.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria salva(Categoria categoria) {
		return repository.save(categoria);
	}
	
	public void deletar(Long id) {
		repository.delete(id);
	}
	
	public List<Categoria> listarTodos(){
		return repository.listAll();
	}

	public Categoria buscarPor(String tag) {
		return repository.findByTag(tag);
	}
	
}
