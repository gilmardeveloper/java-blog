package br.com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.blog.model.Usuario;
import br.com.blog.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public Usuario salvar(Usuario usuario) {
		return repository.save(usuario);
	}
	
	public void deletar(Long id) {
		repository.delete(id);
	}
	
	public Usuario buscarPor(Long id) {
		return repository.findOne(id);
	}
	
	public Usuario buscarPor(String email) {
		return repository.findByEmail(email);
	}
	
	public List<Usuario> listarTodos(){
		return repository.listAll();
	}
	
	
}
