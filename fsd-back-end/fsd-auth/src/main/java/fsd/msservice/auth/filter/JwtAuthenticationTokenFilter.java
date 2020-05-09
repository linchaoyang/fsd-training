package fsd.msservice.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import fsd.model.user.User;
import fsd.msservice.auth.exception.JwtAuthenticationException;
import fsd.msservice.auth.util.JwtHelper;
import io.jsonwebtoken.Claims;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String authHeader = request.getHeader("Authorization");
                String tokenHead = "Bearer ";

                if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith(tokenHead)) {
                    String token = authHeader.substring(tokenHead.length());
                    Claims claims = JwtHelper.parseJWT(token);
                    if (claims != null) {
                        String username = claims.get("username").toString();
                        String role = claims.get("role").toString();
                        String[] rolesArray = role.split(",");
                        // 1. Create UserDetails
                        UserDetails userDetails = new User(username, "N/A", rolesArray);
                        // 2. Get authorized token
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, "N/A", userDetails.getAuthorities());
                        // 3. Set authentication Details
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 4. Write token into context to notice spring security filter chain that
                        // current is authorized already
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (JwtAuthenticationException exception) {
                request.setAttribute("jwterror", exception.getMessage());
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);

    }

}