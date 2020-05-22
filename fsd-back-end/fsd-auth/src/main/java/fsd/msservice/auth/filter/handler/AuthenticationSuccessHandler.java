package fsd.msservice.auth.filter.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import fsd.common.model.ResponseResult;
import fsd.msservice.auth.util.HandlerUtil;
import fsd.msservice.auth.util.JwtHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

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

		String token = JwtHelper.generateToken(user);
		ResponseResult msg = ResponseResult.ok(token);

		HandlerUtil.setResponse(response, HttpStatus.OK, msg);
	}

}