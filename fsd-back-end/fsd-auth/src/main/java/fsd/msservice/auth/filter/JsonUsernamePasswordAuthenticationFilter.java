package fsd.msservice.auth.filter;

import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fsd.msservice.auth.api.config.WebSecurityConfig;
import fsd.msservice.auth.api.domain.UserType;
import fsd.msservice.auth.filter.token.BuyerAuthenticationToken;
import fsd.msservice.auth.filter.token.SellerAuthenticationToken;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * User type key set in the request header
     */
    private static final String HEADER_USER_TYPE = "userType";

    public JsonUsernamePasswordAuthenticationFilter() {
        // Use login: "/api/login", "POST"
        super.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher(WebSecurityConfig.LOGIN_ENTRY_POINT, "POST"));
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return getJsonData(request).get(SPRING_SECURITY_FORM_USERNAME_KEY).toString();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return getJsonData(request).get(SPRING_SECURITY_FORM_PASSWORD_KEY).toString();
    }

    @Override
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        if (request.getHeader(HEADER_USER_TYPE).equals(UserType.Buyer.getType())) {
            authRequest = new BuyerAuthenticationToken(authRequest.getPrincipal(), authRequest.getCredentials());
        } else if (request.getHeader(HEADER_USER_TYPE).equals(UserType.Buyer.getType())) {
            authRequest = new SellerAuthenticationToken(authRequest.getPrincipal(), authRequest.getCredentials());
        }
        super.setDetails(request, authRequest);
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
            final ObjectMapper mapper = new ObjectMapper(); // JSON converter
            final Map<?, ?> map = mapper.readValue(strcont, Map.class);

            return map;
        } catch (final Exception e) {
            throw new AuthenticationServiceException("Login request parameter not exist.");
        }
    }
}