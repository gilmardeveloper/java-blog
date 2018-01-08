package br.com.blog;

import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.SocketUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@SpringBootApplication
@EnableCaching
public class BlogApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	@Bean
	public Integer port() {
		return SocketUtils.findAvailableTcpPort();
	}

	/*@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createStandardConnector());
		return tomcat;
	}

	private Connector createStandardConnector() {
		 Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		    Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
		    try {
		        
		        connector.setScheme("https");
		        connector.setSecure(true);
		        connector.setPort(port());
		        protocol.setSSLEnabled(true);
		        protocol.setKeystoreFile("storekey.jks");
		        protocol.setKeystorePass("cipher");
		        protocol.setTruststoreFile("storekey.jks");
		        protocol.setTruststorePass("cipher");
		        protocol.setKeyAlias("1");
		        return connector;
		    }
		    catch (Exception ex) {
		        throw new IllegalStateException("can't access keystore: [" + "keystore"
		                + "] or truststore: [" + "keystore" + "]", ex);
		    }
	}*/

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(new Locale("pt", "BR"));
		return cookieLocaleResolver;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0);
		return messageSource;
	}

	@Bean
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	@Bean
	public CacheManager cacheManager(Ticker ticker) {
		CaffeineCache postCache = buildCache("postCache", ticker, 10800);
		CaffeineCache parceiroCache = buildCache("parceiroCache", ticker, 10800);
		SimpleCacheManager manager = new SimpleCacheManager();
		manager.setCaches(Arrays.asList(postCache, parceiroCache));
		return manager;
	}

	private CaffeineCache buildCache(String name, Ticker ticker, int minutesToExpire) {
		return new CaffeineCache(name, Caffeine.newBuilder().expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
				.maximumSize(500).ticker(ticker).build());
	}

	@Bean
	public Ticker ticker() {
		return Ticker.systemTicker();
	}

	@Bean
	public DataSource getDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();

		dataSource.setUser("root");
		dataSource.setPassword("toor");
		
		try {
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setJdbcUrl("jdbc:mysql://localhost/blogbd?useUnicode=yes&characterEncoding=UTF-8");

		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(10);
		dataSource.setNumHelperThreads(5);
		dataSource.setIdleConnectionTestPeriod(100);
		
		return dataSource;
	}
}
