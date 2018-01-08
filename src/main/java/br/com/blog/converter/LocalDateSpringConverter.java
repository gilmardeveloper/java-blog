package br.com.blog.converter;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateSpringConverter implements Converter<String, LocalDate> {

	@Override
	public LocalDate convert(String value) {
		return LocalDate.parse(value);
	}

}
