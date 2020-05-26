package fsd.msservice.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

import fsd.common.config.AuthPathConfig;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(AuthPathConfig.class)
public class FsdAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsdAuthApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
