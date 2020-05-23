package fsd.msservice.cart.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("fsd.msservice.cart.api.repository")
public class ApplicationConfig {

}