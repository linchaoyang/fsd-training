package fsd.msservice.auth.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import fsd.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {
	
	@Autowired
	private ObjectMapper objectMapper;
 
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error(accessDeniedException.getMessage());
		ResponseResult msg = ResponseResult.error("No authority to access");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(msg));
	}
 
    
}