package fsd.msservice.auth.filter.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import fsd.common.model.ResponseResult;
import fsd.msservice.auth.util.HandlerUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// This is invoked when user tries to access a secured REST resource without
		// supplying any credentials
		// We should just send a 401 Unauthorized response because there is no 'login
		// page' to redirect to
		log.info(authException.getMessage());
		ResponseResult msg;
		HttpStatus status;
		Object jwtErr = request.getAttribute("jwterror");
		if (jwtErr != null) {
			msg = ResponseResult.error(jwtErr.toString());
			// 500
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} else {
			msg = ResponseResult.error("Need login");
			// 401
			status = HttpStatus.UNAUTHORIZED;
		}

		HandlerUtil.setResponse(response, status, msg);

	}

}