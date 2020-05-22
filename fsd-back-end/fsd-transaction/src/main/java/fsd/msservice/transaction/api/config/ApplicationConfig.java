package fsd.msservice.transaction.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("fsd.msservice.transaction.api.repository")
public class ApplicationConfig {

}