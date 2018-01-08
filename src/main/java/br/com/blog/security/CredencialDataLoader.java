package br.com.blog.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.blog.model.Autorizacao;
import br.com.blog.model.Privilegio;
import br.com.blog.model.Usuario;
import br.com.blog.repository.AutorizacaoRepository;
import br.com.blog.repository.PrivilegioRepository;
import br.com.blog.repository.UsuarioRepository;

@Component
public class CredencialDataLoader implements  ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
 
    @Autowired
    private UsuarioRepository usuarioRepository;
  
    @Autowired
    private AutorizacaoRepository autorizacaoRepository;
  
    @Autowired
    private PrivilegioRepository privelegioRepository;
    
    @Autowired
    private PasswordCrypt passwordCrypt;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        if (alreadySetup) return;
                
        Privilegio readPrivilege = createPrivilegeIfNotFound("ROLE_ADMIN");
        Privilegio writePrivilege = createPrivilegeIfNotFound("ROLE_USER");
  
        List<Privilegio> privilegios = Arrays.asList(readPrivilege, writePrivilege);        
        
        createRoleIfNotFound("ROLE_ADMIN", privilegios);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(writePrivilege));
 
        Autorizacao adminRole = autorizacaoRepository.findByNome("ROLE_ADMIN");
               
        Usuario user = new Usuario();
        user.setNome("Administrador");
        user.setSenha(passwordCrypt.encode("12345"));
        user.setEmail("admin@mail.com");
        user.setAutorizacoes(Arrays.asList(adminRole));
        user.setHabilitado(true);
        
        if(usuarioRepository.findByEmail(user.getEmail()) != null)
        	user = usuarioRepository.findByEmail(user.getEmail());
        
        usuarioRepository.save(user);
 
        alreadySetup = true;
    }
 
    @Transactional
    private Privilegio createPrivilegeIfNotFound(String nome) {
  
        Privilegio privilegio = privelegioRepository.findByNome(nome);
        if (privilegio == null) {
            privilegio = new Privilegio(nome);
            privelegioRepository.save(privilegio);
        }
        return privilegio;
    }
 
    @Transactional
    private Autorizacao createRoleIfNotFound(
      String name, Collection<Privilegio> privilegios) {
  
        Autorizacao autorizacao = autorizacaoRepository.findByNome(name);
        if (autorizacao == null) {
            autorizacao = new Autorizacao(name);
            autorizacao.setPrivilegios(privilegios);
            autorizacaoRepository.save(autorizacao);
        }
        return autorizacao;
    }
}
