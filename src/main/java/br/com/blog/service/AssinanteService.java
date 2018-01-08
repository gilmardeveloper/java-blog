package br.com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.blog.model.Assinante;
import br.com.blog.repository.AssinanteRepository;

@Service
public class AssinanteService {

	@Autowired
	private AssinanteRepository repository;
	
	public String salva(Assinante assinante) {
		if(repository.findByEmail(assinante.getEmail()) != null) 
			return "Seu email já foi cadastrado.";
		else { 
			repository.save(assinante);
			return "Seu email foi cadastrado com sucesso, aguarde nossas novidades.";
		}
	}
	
	public void deletar(Long id) {
		repository.delete(id);
	}
	
	public List<Assinante> listarTodos(){
		return repository.listAll();
	}
	
	
	
}
