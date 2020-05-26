package fsd.msservice.auth.filter.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import fsd.common.model.Result;
import fsd.common.model.auth.FsdUserDetail;
import fsd.common.util.ResponseUtil;
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
		log.info("Login success: username= {}, RemoteAddr= {}, RemoteHost= {}, RemotePort={}", user.getUsername(),
				request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());

//		String token = JwtHelper.generateToken(user);
//		ResponseResult msg = ResponseResult.ok(token);

		Result<HashMap<String, Object>> data = Result.ok(new HashMap<String, Object>() {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -4797435388368590364L;

			{
				put("userId", ((FsdUserDetail) user).getUserId());
				put("userType", ((FsdUserDetail) user).getUserType());
				put("username", ((FsdUserDetail) user).getUsername());
				put("authorities", AuthorityUtils.authorityListToSet(((FsdUserDetail) user).getAuthorities()));
			}

		});

		ResponseUtil.setResponse(response, HttpStatus.OK, data);
	}

}