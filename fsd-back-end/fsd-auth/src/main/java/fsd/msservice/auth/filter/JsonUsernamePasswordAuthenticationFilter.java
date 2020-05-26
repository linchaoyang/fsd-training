package fsd.msservice.auth.filter;

import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import fsd.common.config.AuthPathConfig;
import fsd.common.model.auth.UserType;
import fsd.msservice.auth.filter.token.BuyerAuthenticationToken;
import fsd.msservice.auth.filter.token.SellerAuthenticationToken;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private ObjectMapper objectMapper;

	/**
	 * User type key set in the request header
	 */
	private static final String HEADER_USER_TYPE = "userType";

	public JsonUsernamePasswordAuthenticationFilter(AuthPathConfig authPathConfig, ObjectMapper objectMapper) {

		// Use login: "/auth/login", "POST"
		super(new AntPathRequestMatcher(authPathConfig.getLoginPath().get(0).getUrl().get(0), "POST"));

		this.objectMapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		final Map<?, ?> loginInfo = getJsonData(request);
		String username = loginInfo.get(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
				.toString();
		String password = loginInfo.get(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
				.toString();

		AbstractAuthenticationToken authRequest;

		String userType = request.getHeader(HEADER_USER_TYPE);
		if (UserType.Buyer.getType().equals(userType)) {
			authRequest = new BuyerAuthenticationToken(username, password);
		} else if (UserType.Seller.getType().equals(userType)) {
			authRequest = new SellerAuthenticationToken(username, password);
		} else {
			throw new AuthenticationServiceException("Login request header parameter 'userType' not exist.");
		}

		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private Map<?, ?> getJsonData(final HttpServletRequest request) {
		try {
			final ServletInputStream ris = request.getInputStream();
			final StringBuilder content = new StringBuilder();
			final byte[] b = new byte[1024];
			int lens = -1;
			while ((lens = ris.read(b)) > 0) {
				content.append(new String(b, 0, lens));
			}
			final String strcont = content.toString();// content
			final Map<?, ?> map = objectMapper.readValue(strcont, Map.class);

			return map;
		} catch (final Exception e) {
			throw new AuthenticationServiceException("Login request parameter not exist.");
		}
	}
}