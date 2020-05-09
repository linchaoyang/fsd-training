package fsd.msservice.auth.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import fsd.model.ResponseResult;
import fsd.msservice.auth.util.JwtHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.
     * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
                UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info("Login success: username=" + user.getUsername() + " RemoteAddr=" + request.getRemoteAddr()
                + " RemoteHost=" + request.getRemoteHost() + " RemotePort=" + request.getRemotePort());
        
        String token = JwtHelper.createJWT(user);
        ResponseResult msg = ResponseResult.ok(token);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(msg));

    }

}