package br.com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.blog.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
	
	@Query("select p from Post p order by p.dataPostagem desc")
	List<Post> listAll();

	Post findByUrl(String url);
	
	List<Post> findLast15ByOrderByDataPostagemDesc();
	
}
