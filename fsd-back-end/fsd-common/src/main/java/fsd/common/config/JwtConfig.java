package fsd.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {

	private int expire;

	private String salt;

	private String issuer;
}
