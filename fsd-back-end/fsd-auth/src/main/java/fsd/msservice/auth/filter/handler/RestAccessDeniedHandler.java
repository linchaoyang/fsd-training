package fsd.msservice.auth.filter.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import fsd.common.model.ResponseResult;
import fsd.msservice.auth.util.HandlerUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error(accessDeniedException.getMessage());
		ResponseResult msg = ResponseResult.error("No authority to access");

		HandlerUtil.setResponse(response, HttpStatus.FORBIDDEN, msg);
	}

}