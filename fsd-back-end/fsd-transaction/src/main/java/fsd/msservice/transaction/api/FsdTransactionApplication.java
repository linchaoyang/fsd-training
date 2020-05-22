package fsd.msservice.transaction.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import fsd.common.config.WebSecurityConfig;
import fsd.msservice.transaction.api.config.ApplicationConfig;

@SpringBootApplication
@Import({ WebSecurityConfig.class, ApplicationConfig.class })
public class FsdTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsdTransactionApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
