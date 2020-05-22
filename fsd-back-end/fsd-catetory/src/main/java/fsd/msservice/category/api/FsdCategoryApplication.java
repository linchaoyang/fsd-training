package fsd.msservice.category.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import fsd.common.config.WebSecurityConfig;

@SpringBootApplication
@Import({ WebSecurityConfig.class })
public class FsdCategoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsdCategoryApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
