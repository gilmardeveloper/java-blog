package br.com.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.blog.model.Usuario;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	public void enviar(Usuario usuario, String token) {
		
		String recipientAddress = usuario.getEmail();
        String subject = "Registration Confirmation";
        String url = "/login/confirmar-registro.html?token=" + token;
                
        SimpleMailMessage email = new SimpleMailMessage();
                
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Você se cadastrou no Blog, para finalizar o registro, favor clicar no link ao lado " + "http://blog.com.br" + url);
        mailSender.send(email);
	}

}
