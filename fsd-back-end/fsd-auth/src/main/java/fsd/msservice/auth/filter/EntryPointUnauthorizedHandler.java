package fsd.msservice.auth.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import fsd.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // This is invoked when user tries to access a secured REST resource without
        // supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login
        // page' to redirect to
        log.info(authException.getMessage());
        ResponseResult msg;
        Object jwtErr = request.getAttribute("jwterror");
        if (jwtErr != null) {
            msg = ResponseResult.error(jwtErr.toString());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {
            msg = ResponseResult.error("Nedd login");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(msg));
    }

}