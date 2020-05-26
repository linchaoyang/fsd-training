package fsd.msservice.zuul.filter;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import fsd.common.config.AuthPathConfig;
import fsd.common.config.JwtConfig;
import fsd.common.model.Result;
import fsd.common.util.JwtHelper;
import fsd.common.util.RequestMatcherUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAfterLoginPostFilter extends ZuulFilter {

	public static final String HEADER_AUTHORIZATION = "Authorization";

	public static final String TOKEN_HEAD = "Bearer ";

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
		return FilterConstants.POST_TYPE;
	}

	/**
	 * filterOrder
	 *
	 * @return
	 */
	@Override
	public int filterOrder() {
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
	}

	/**
	 * filter to judge whether execute run
	 *
	 * @return true to excute run
	 */
	@Override
	public boolean shouldFilter() {
		// if match the login path, then execute run
		RequestContext ctx = RequestContext.getCurrentContext();

		HttpServletRequest request = ctx.getRequest();
		log.debug(String.format("%s JwtAfterLoginPostFilter request to %s", request.getMethod(),
				request.getRequestURL().toString()));

		List<RequestMatcher> requestMatchers = RequestMatcherUtil.requestMatchers(authPathConfig.getLoginPath());

		for (RequestMatcher matcher : requestMatchers) {

			if (matcher.matches(ctx.getRequest())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		String body = "";
		try {
			InputStream stream = ctx.getResponseDataStream();
			body = StreamUtils.copyToString(stream, StandardCharsets.UTF_8);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		if (ctx.getResponseStatusCode() != HttpStatus.OK.value()) {
			log.info("Login authorize failed");

		} else {
			log.info("Login authorize success");
			try {
				// InputStream stream = ctx.getResponseDataStream();
				/*
				 * { "timestamp": 1590514783658, "code": 0, "message": "success", "data": {
				 * "userType": "0", "userId": "daf49ca9-7898-44d5-bd2b-f8a92300ed54",
				 * "authorities": [ "ROLE_BUYER" ], "username": "zz" } }
				 * 
				 */
				// String body = StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
				Result<HashMap<String, Object>> result = objectMapper.readValue(body,
						new TypeReference<Result<HashMap<String, Object>>>() {
						});
				// login success
				if (result.getCode() == Result.CODE_SUCCESS) {
					HashMap<String, Object> jwtClaims = new HashMap<String, Object>() {
						/**
						 * serialVersionUID
						 */
						private static final long serialVersionUID = 1L;

						{
							put("userId", result.getData().get("userId"));
							put("userType", result.getData().get("userType"));
							put("sub", result.getData().get("username"));
							put("authorities", result.getData().get("authorities"));
						}
					};
					String token = new JwtHelper(jwtConfig).generateToken(jwtClaims);
					// body json增加token
					result.getData().put("token", token);
					// 序列化body json,设置到响应body中
					body = objectMapper.writeValueAsString(result);
					ctx.setResponseBody(body);

					// 响应头设置token
					ctx.addZuulResponseHeader(HEADER_AUTHORIZATION, TOKEN_HEAD + token);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

		return null;
	}

}
