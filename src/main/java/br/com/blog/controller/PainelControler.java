package br.com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.blog.model.Assinante;
import br.com.blog.model.Categoria;
import br.com.blog.model.Parceiro;
import br.com.blog.model.Post;
import br.com.blog.service.AssinanteService;
import br.com.blog.service.CategoriaService;
import br.com.blog.service.ParceiroService;
import br.com.blog.service.PostService;

@Controller
@RequestMapping("/painel")
@SessionScope
public class PainelControler {
	
	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ParceiroService parceiroService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private AssinanteService assinanteService;
	
	@GetMapping(value= {"/", ""})
	public String post(Model model) {
		model.addAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("posts", postService.listarTodos());
		return "painel";
	}
	
	@GetMapping(value= {"/assinantes/", "/assinantes"})
	@ResponseBody
	public List<Assinante> assinantes(Model model) {
		return assinanteService.listarTodos();
	}
	
	@GetMapping(value= {"/parceiros/", "/parceiros"})
	@ResponseBody
	public List<Parceiro> parceiros(Model model) {
		return parceiroService.listarTodos();
	}
	
	@PostMapping("/categoria/salvar/")
	public String salvar(Categoria categoria, RedirectAttributes model) {
		categoriaService.salva(categoria);
		model.addFlashAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("posts", postService.listarTodos());
		return "redirect:/painel";
	}
	
	@PostMapping("/parceiro/salvar/")
	public String salvar(Parceiro parceiro, RedirectAttributes model) {
		parceiroService.salva(parceiro);
		model.addFlashAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("posts", postService.listarTodos());
		return "redirect:/painel";
	}
	
	@PostMapping("/post/salvar/")
	public String salvar(Post post, RedirectAttributes model) {
		postService.salva(post);
		model.addFlashAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("posts", postService.listarTodos());
		return "redirect:/painel";
	}
	
	@PostMapping("/post/preview/")
	public String preview(Post post, RedirectAttributes model) {
		model.addFlashAttribute("post", post);
		return "template-post";
	}
	
	@GetMapping(value= {"/post/{id}/visualizar/", "/post/{id}/visualizar"})
	public String preview(@PathVariable("id") Long id, RedirectAttributes model) {
		model.addFlashAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("posts", postService.listarTodos());
		model.addFlashAttribute("post", postService.buscarPor(id));
		return "redirect:/painel";
	}
	
	@GetMapping(value = {"post/{id}/remover/", "post/{id}/remover"})
	public String remover(@PathVariable("id") Long id, RedirectAttributes model) {
		postService.deletar(id);
		model.addFlashAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("posts", postService.listarTodos());
		return "redirect:/painel";
	}

}
