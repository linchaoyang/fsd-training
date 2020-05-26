package fsd.common.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.sun.istack.Nullable;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "app.auth-path-config")
public class AuthPathConfig {

	private List<RequestPathPatternConfig> loginPath;

	private List<RequestPathPatternConfig> nonAuthPath;

	@Data
	public static class RequestPathPatternConfig {

		@Nullable
		private List<String> method;

		@Nullable
		private List<String> url;
	}
}
