package br.com.blog.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import br.com.blog.model.Post;
import br.com.blog.service.AssinanteService;
import br.com.blog.service.CategoriaService;
import br.com.blog.service.PaginacaoService;
import br.com.blog.service.PostService;
import br.com.blog.sitemap.XmlUrl;
import br.com.blog.sitemap.XmlUrlSet;

@Controller
@RequestMapping("/")
@SessionScope
public class PaginaControler {
	
	private final static Integer MAXIMO_PAGES = 6;
	
	@Autowired
	private AssinanteService assinanteService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PaginacaoService paginacaoService;
	
	private Page<Post> getPaginacao(Integer page) {
		return paginacaoService.listarTodos(new PageRequest(page, MAXIMO_PAGES));
	}
	
	@PostMapping("/pesquisar/")
	public String buscar(String assunto, Model model) {
		model.addAttribute("posts", paginacaoService.pesquisar(assunto));
		model.addAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("recentes", postService.listarRecentes());
		return "template-index";
	}
	
	@GetMapping(value = {"/categorias/{tag}/", "/categorias/{tag}" })
	public String postsPorCategoria(@PathVariable("tag") String tag, Model model) {
		model.addAttribute("posts", paginacaoService.buscarPor(categoriaService.buscarPor(tag)));
		model.addAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("recentes", postService.listarRecentes());
		return "template-index";
	}
	
	@GetMapping(value= {"page/{page}/", "page/{page}"})
	public String next(@PathVariable("page") Integer page, Model model) {
		model.addAttribute("posts", getPaginacao(page));
		model.addAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("recentes", postService.listarRecentes());
		return "template-index";
	}
		
	@GetMapping
	public String index(Model model) {
		model.addAttribute("posts", getPaginacao(0));
		model.addAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("recentes", postService.listarRecentes());
		return "template-index";
	}
	
	@GetMapping(value = {"{categoria}/{url}/","{categoria}/{url}"})
	public String post(@PathVariable("categoria") String categoria, @PathVariable("url") String url, Model model) {
		model.addAttribute("post", postService.buscarPor(url));
		model.addAttribute("categorias", categoriaService.listarTodos());
		model.addAttribute("recentes", postService.listarRecentes());
		return "template-post";
	}
	
	@RequestMapping(value = {"/robots", "/robot", "/robot.txt", "/robots.txt", "/null"})
	public void robot(HttpServletResponse response) {
	 
	    InputStream resourceAsStream = null;
	    try {
	 
	        ClassLoader classLoader = getClass().getClassLoader();
	        resourceAsStream = classLoader.getResourceAsStream("robots.txt");
	        response.addHeader("Content-disposition", "filename=robot.txt");
	        response.setContentType("text/plain");
	        IOUtils.copy(resourceAsStream, response.getOutputStream());
	        response.flushBuffer();
	        
	    } catch (Exception e) {
	        System.err.println("Problem with displaying robot.txt" + e);
	    } finally {
	        try {
				resourceAsStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	@GetMapping(value = {"/sitemap.xml", "/sitemap.xml/"})
    @ResponseBody
    public XmlUrlSet main() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        create(xmlUrlSet, "/", XmlUrl.Priority.HIGH);
       
        postService.listarTodos().forEach(p -> create(xmlUrlSet, "/" + p.getCategoria().getTag() + "/" + p.getUrl() + "/", XmlUrl.Priority.MEDIUM) );
        categoriaService.listarTodos().forEach(c -> create(xmlUrlSet, "/categorias/" + c.getTag() + "/", XmlUrl.Priority.MEDIUM) );
        
        for(int i = 0; i < getPaginacao(0).getTotalPages(); i++) {
        	create(xmlUrlSet, "/page/" + getPaginacao(i).getNumber() + "/", XmlUrl.Priority.MEDIUM);
        }
        
        return xmlUrlSet;
    }

    private void create(XmlUrlSet xmlUrlSet, String link, XmlUrl.Priority priority) {
        xmlUrlSet.addUrl(new XmlUrl("http://blog.com.br" + link, priority));
    }
    
    @PostMapping("/escricao/assinante/")
    public String salvar(Assinante assinante, RedirectAttributes model) {
    	model.addFlashAttribute("alerta", assinanteService.salva(assinante));
    	return "redirect:/";
    }
    
}
