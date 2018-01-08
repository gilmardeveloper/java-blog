package br.com.blog.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

@Entity
public class Post implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String tituloPagina;
	private String metaDescricao;
	private String imagemCabecalho;
	private String corCabecalho;
	private String imagemAssunto;
	private String imagemAssuntoAlt;
	private String imagemAssuntoTitle;
	private String tituloConteudo;
	private String subtituloConteudo;
	private String autor;
	
	@Type(type="text")
	private String conteudo;
	
	@Type(type="text")
	private String descricaoConteudo;
	
	private LocalDate dataPostagem;
	private String url;
	
	@OneToOne
	private Categoria categoria;
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTituloPagina() {
		return tituloPagina;
	}

	public void setTituloPagina(String tituloPagina) {
		this.tituloPagina = tituloPagina;
	}

	public String getMetaDescricao() {
		return metaDescricao;
	}

	public void setMetaDescricao(String metaDescricao) {
		this.metaDescricao = metaDescricao;
	}

	public String getImagemCabecalho() {
		return imagemCabecalho;
	}

	public void setImagemCabecalho(String imagemCabecalho) {
		this.imagemCabecalho = imagemCabecalho;
	}

	public String getCorCabecalho() {
		return corCabecalho;
	}

	public void setCorCabecalho(String corCabecalho) {
		this.corCabecalho = corCabecalho;
	}

	public String getImagemAssunto() {
		return imagemAssunto;
	}

	public void setImagemAssunto(String imagemAssunto) {
		this.imagemAssunto = imagemAssunto;
	}

	public String getImagemAssuntoAlt() {
		return imagemAssuntoAlt;
	}

	public void setImagemAssuntoAlt(String imagemAssuntoAlt) {
		this.imagemAssuntoAlt = imagemAssuntoAlt;
	}

	public String getImagemAssuntoTitle() {
		return imagemAssuntoTitle;
	}

	public void setImagemAssuntoTitle(String imagemAssuntoTitle) {
		this.imagemAssuntoTitle = imagemAssuntoTitle;
	}

	public String getTituloConteudo() {
		return tituloConteudo;
	}

	public void setTituloConteudo(String tituloConteudo) {
		this.tituloConteudo = tituloConteudo;
	}

	public String getSubtituloConteudo() {
		return subtituloConteudo;
	}

	public String getDescricaoConteudo() {
		return descricaoConteudo;
	}

	public void setDescricaoConteudo(String descricaoConteudo) {
		this.descricaoConteudo = descricaoConteudo;
	}

	public void setSubtituloConteudo(String subtituloConteudo) {
		this.subtituloConteudo = subtituloConteudo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public LocalDate getDataPostagem() {
		return dataPostagem;
	}

	public void setDataPostagem(LocalDate dataPostagem) {
		this.dataPostagem = dataPostagem;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
