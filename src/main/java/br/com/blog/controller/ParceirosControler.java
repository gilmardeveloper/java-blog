package br.com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

import br.com.blog.model.Parceiro;
import br.com.blog.service.CategoriaService;
import br.com.blog.service.ParceiroService;
import br.com.blog.service.PostService;

@Controller
@RequestMapping("/parceiros")
@SessionScope
public class ParceirosControler {
	
	private final static Integer MAXIMO_PAGES = 10;
	
	@Autowired
	private ParceiroService parceiroService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	private Page<Parceiro> getPaginacao(Integer page) {
		return parceiroService.listarTodos(new PageRequest(page, MAXIMO_PAGES));
	}
	
	@GetMapping(value = {"/",""})
	public String parceiros(Model model) {
		model.addAttribute("parceiros", getPaginacao(0));
		model.addAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("recentes", postService.listarRecentes());
		return "template-parceiros";
	}
	
	@GetMapping(value= {"/parceiros/page/{page}/", "/parceiros/page/{page}"})
	public String next(@PathVariable("page") Integer page, Model model) {
		model.addAttribute("parceiros", getPaginacao(page));
		model.addAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("recentes", postService.listarRecentes());
		return "template-index";
	}
}
