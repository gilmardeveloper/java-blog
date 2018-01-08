package br.com.blog.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.blog.model.Post;
import br.com.blog.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;
	
	@CacheEvict(value="postCache", allEntries=true)
	public Post salva(Post post) {
		if(post.getDataPostagem() == null)
			post.setDataPostagem(LocalDate.now());

		return repository.save(post);
	}
	
	@CacheEvict(value="postCache", allEntries=true)
	public void deletar(Long id) {
		repository.delete(id);
	}
	
	@Cacheable("postCache")
	public List<Post> listarTodos(){
		return repository.listAll();
	}

	@Cacheable("postCache")
	public List<Post> listarRecentes(){
		return repository.findLast15ByOrderByDataPostagemDesc();
	}
	
	public Post buscarPor(Long id) {
		return repository.findOne(id);
	}
	
	@Cacheable("postCache")
	public Post buscarPor(String url) {
		return repository.findByUrl(url);
	}
	
	
	
}
