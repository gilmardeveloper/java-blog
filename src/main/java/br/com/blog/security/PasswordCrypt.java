package br.com.blog.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordCrypt extends BCryptPasswordEncoder{
	

}
