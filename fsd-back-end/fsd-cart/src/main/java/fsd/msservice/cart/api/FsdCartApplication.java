package fsd.msservice.cart.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import fsd.common.config.WebSecurityConfig;
import fsd.msservice.cart.api.config.ApplicationConfig;

@SpringBootApplication
@Import({ WebSecurityConfig.class, ApplicationConfig.class })
public class FsdCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsdCartApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
