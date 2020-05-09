package fsd.msservice.auth.filter;

import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JsonUsernamePasswordAuthenticationFilter() {
        // Use super login: "/login", "POST"
        super();
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return getJsonData(request).get(SPRING_SECURITY_FORM_USERNAME_KEY).toString();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return getJsonData(request).get(SPRING_SECURITY_FORM_PASSWORD_KEY).toString();
    }

    private Map<?, ?> getJsonData(HttpServletRequest request) {
        try {
            ServletInputStream ris = request.getInputStream();
            StringBuilder content = new StringBuilder();
            byte[] b = new byte[1024];
            int lens = -1;
            while ((lens = ris.read(b)) > 0) {
                content.append(new String(b, 0, lens));
            }
            String strcont = content.toString();// content
            ObjectMapper mapper = new ObjectMapper(); // JSON converter
            Map<?, ?> map = mapper.readValue(strcont, Map.class);

            return map;
        } catch (Exception e) {
            throw new AuthenticationServiceException("Login request parameter not exist.");
        }
    }
}