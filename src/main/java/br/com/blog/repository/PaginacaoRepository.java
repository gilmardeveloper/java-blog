package br.com.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.blog.model.Categoria;
import br.com.blog.model.Post;

public interface PaginacaoRepository extends PagingAndSortingRepository<Post, Long>{
	
	@Query("select p from Post p order by p.dataPostagem desc")
	Page<Post> listarTodos(Pageable pageable);

	Page<Post> findByCategoria(Categoria categoria, Pageable pageable);
	
	@Query("select p from Post p where p.tituloConteudo like %:passunto% order by p.dataPostagem desc")
	Page<Post> pesquisar(@Param("passunto") String assunto, Pageable pageable);

}
