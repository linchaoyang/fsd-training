package fsd.msservice.user.api.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Close cors, allow all request access
                .cors().and()
                // Since use JWT, csrf is disabled
                .csrf().disable()
                // Since use token, don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // inner microservice, no need any more authentication
                .and().authorizeRequests().anyRequest().permitAll();
    }
}