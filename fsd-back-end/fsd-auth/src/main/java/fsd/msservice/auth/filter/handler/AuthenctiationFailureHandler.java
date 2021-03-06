package fsd.msservice.auth.filter.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import fsd.common.model.Result;
import fsd.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.authentication.AuthenticationFailureHandler#
	 * onAuthenticationFailure(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		log.info("Login failure: {}", exception);

		Result<?> msg = Result.error(exception.getClass().getName(), exception.getMessage());

		ResponseUtil.setResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, msg);
	}

}