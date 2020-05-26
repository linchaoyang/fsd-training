package fsd.common.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ObjectUtils;

import fsd.common.config.AuthPathConfig.RequestPathPatternConfig;

public final class RequestMatcherUtil {

	public static List<RequestMatcher> requestMatchers(List<RequestPathPatternConfig> requestPathConfigs) {

		List<RequestMatcher> requestMatchers = new ArrayList<>();

		if (ObjectUtils.isEmpty(requestPathConfigs)) {
			return requestMatchers;
		}

		for (RequestPathPatternConfig config : requestPathConfigs) {
			String[] antPatterns = config.getUrl() == null ? new String[] { "/**" }
					: config.getUrl().toArray(new String[0]);

			if (config.getMethod() != null) {
				for (String method : config.getMethod()) {
					antMatchers(requestMatchers, method, antPatterns);
				}

			} else {
				antMatchers(requestMatchers, null, antPatterns);
			}
		}

		return requestMatchers;
	}

	private static void antMatchers(List<RequestMatcher> matchers, String method, String... antPatterns) {
		for (String pattern : antPatterns) {
			matchers.add(new AntPathRequestMatcher(pattern, method));
		}
	}
}
