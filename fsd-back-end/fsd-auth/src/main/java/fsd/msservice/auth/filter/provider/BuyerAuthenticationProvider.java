package fsd.msservice.auth.filter.provider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import fsd.msservice.auth.filter.token.BuyerAuthenticationToken;

public class BuyerAuthenticationProvider extends DaoAuthenticationProvider {

    public BuyerAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        setPasswordEncoder(passwordEncoder);
        setUserDetailsService(userDetailsService);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BuyerAuthenticationToken.class.isAssignableFrom(authentication);
    }

}