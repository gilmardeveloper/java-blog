package br.com.blog.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
public class AutenticacaoErro extends SimpleUrlAuthenticationFailureHandler{

	@Autowired
    private MessageSource messages;
	
	@Autowired
	private LocaleResolver localeResolver;
		
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		setDefaultFailureUrl("/login.html?error=true");
		 
        super.onAuthenticationFailure(request, response, exception);
 
        Locale locale = localeResolver.resolveLocale(request);
 
        String errorMessage = messages.getMessage("message.badCredentials", null, locale);
        
        System.out.println(request.getLocalAddr());
        
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = messages.getMessage("auth.message.disabled", null, locale);
            
        }else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = messages.getMessage("auth.message.expired", null, locale);
            
        }else if (exception.getMessage().equalsIgnoreCase("Favor aguarde 15 minutos até a próxima tentativa")) {
            errorMessage = messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", null, locale);
            
        }
                
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
        
	}
	
}
