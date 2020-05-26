package fsd.msservice.auth.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import fsd.common.model.auth.FsdUserDetail;
import fsd.msservice.auth.exception.JwtAuthenticationException;
import fsd.msservice.auth.filter.service.BuyerDetailsServiceImpl;
import fsd.msservice.auth.filter.service.SellerDetailsServiceImpl;
import fsd.msservice.auth.util.JwtHelper;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private static final String HEADER_AUTHORIZATION = "Authorization";

	private static final String TOKEN_HEAD = "Bearer ";

	@Resource
	private BuyerDetailsServiceImpl buyerDetailsService;

	@Resource
	private SellerDetailsServiceImpl sellerDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				final String authHeader = request.getHeader(HEADER_AUTHORIZATION);
				if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith(TOKEN_HEAD)) {
					final String token = authHeader.substring(TOKEN_HEAD.length());
					// Claims claims = JwtHelper.getClaimsFromToken(token);
					final String username = JwtHelper.getUsernameFromToken(token);
					if (username != null) {

//						UserDetails userDetails = null;
//
//						
//						// 1. Create UserDetails based on the user info from database
//						if (userType.equals(UserType.Buyer.getType())) {
//							// Buyer
//							userDetails = buyerDetailsService.loadUserByUsername(username);
//						} else if (userType.equals(UserType.Seller.getType())) {
//							// Seller
//							userDetails = sellerDetailsService.loadUserByUsername(username);
//						}
//						if (JwtHelper.validateToken(token, userDetails)) {
						if (!JwtHelper.isTokenExpired(token)) {

							final String userType = JwtHelper.getUserTypeFromToken(token);
							String userId = JwtHelper.getUserIdFromToken(token);
							String[] roleNameArray = JwtHelper.getAuthoritiesFromToken(token);
							UserDetails userDetails = new FsdUserDetail(userType, userId, username, null,
									roleNameArray);

							// 2. Get authorized token
							final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());
							// 3. Set authentication Details
							authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							// 4. Write token into context to notice spring security filter chain that
							// current is authorized already
							SecurityContextHolder.getContext().setAuthentication(authentication);
						}
					}
				}
			} catch (final JwtAuthenticationException exception) {
				request.setAttribute("jwterror", exception.getMessage());
				// Access-Control-Allow-Origin
				response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
				// "application/json;charset=UTF-8"
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

}