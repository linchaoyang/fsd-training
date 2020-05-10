package fsd.msservice.auth.filter.provider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import fsd.msservice.auth.filter.token.SellerAuthenticationToken;

public class SellerAuthenticationProvider extends DaoAuthenticationProvider {

    public SellerAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        setPasswordEncoder(passwordEncoder);
        setUserDetailsService(userDetailsService);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SellerAuthenticationToken.class.isAssignableFrom(authentication);
    }

}