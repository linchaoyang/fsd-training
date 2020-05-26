package fsd.msservice.zuul.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import fsd.common.config.AuthPathConfig;
import fsd.common.config.JwtConfig;
import fsd.common.model.Result;
import fsd.common.util.JwtHelper;
import fsd.common.util.RequestMatcherUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthPreFilter extends ZuulFilter {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	AuthPathConfig authPathConfig;

	@Autowired
	JwtConfig jwtConfig;

	/**
	 * <ul>
	 * <li>pre：before routing
	 * <li>routing：when routing
	 * <li>post： after routing
	 * <li>error：send error
	 * </ul>
	 * 
	 * @return
	 */
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	/**
	 * filterOrder
	 *
	 * @return
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * filter to judge whether execute run
	 *
	 * @return true to excute run
	 */
	@Override
	public boolean shouldFilter() {
		// if not match the login and static resource path, then execute run
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.debug(String.format("%s JwtAuthPreFilter request to %s", request.getMethod(),
				request.getRequestURL().toString()));

		List<RequestMatcher> requestMatchers = new ArrayList<>();
		requestMatchers.addAll(RequestMatcherUtil.requestMatchers(authPathConfig.getLoginPath()));
		requestMatchers.addAll(RequestMatcherUtil.requestMatchers(authPathConfig.getLoginPath()));

		for (RequestMatcher matcher : requestMatchers) {

			if (matcher.matches(ctx.getRequest())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		final String authHeader = request.getHeader(JwtAfterLoginPostFilter.HEADER_AUTHORIZATION);
		if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith(JwtAfterLoginPostFilter.TOKEN_HEAD)) {
			// get token from header
			final String token = authHeader.substring(JwtAfterLoginPostFilter.TOKEN_HEAD.length());

			Claims claims;
			try {
				JwtHelper jwtUtil = new JwtHelper(jwtConfig);
				// no exception occurred, means token is valid
				claims = jwtUtil.getClaimsFromToken(token);
				log.info("token : {} passed validation", token);

				// TODO validate token in the cache

				// pass to router to do business api
				ctx.setSendZuulResponse(true);
				// add userId and username into request header to pass to business api
				ctx.addZuulRequestHeader("userId", claims.get("userId").toString());
				ctx.addZuulRequestHeader("username", claims.getSubject());

			} catch (ExpiredJwtException expiredJwtEx) {
				log.error("token : {} expired", token);
				// stop request routing
				ctx.setSendZuulResponse(false);
				responseError(ctx, HttpStatus.UNAUTHORIZED, "token expired");
			} catch (Exception ex) {
				log.error("token : {} validate failed", token);
				// stop request routing
				ctx.setSendZuulResponse(false);
				responseError(ctx, HttpStatus.UNAUTHORIZED, "invalid token");
			}
		} else {
			log.error("token not exist");
			// stop request routing
			ctx.setSendZuulResponse(false);
			responseError(ctx, HttpStatus.UNAUTHORIZED, "no token");
		}

		return null;
	}

	/**
	 * 将异常信息响应给前端
	 */
	private void responseError(RequestContext ctx, HttpStatus status, String message) {
		HttpServletResponse response = ctx.getResponse();

		// Access-Control-Allow-Origin
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		// "application/json;charset=UTF-8"
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		// set response code
		response.setStatus(status.value());

		Result<?> errResult = Result.error(status.getReasonPhrase(), message);
		ctx.setResponseBody(toJsonString(errResult));
//		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
	}

	private String toJsonString(Result<?> o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			log.error("json serialize failed", e);
			return null;
		}
	}

}
