package fsd.msservice.auth.api.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fsd.msservice.auth.api.domain.UserType;
import lombok.Setter;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Setter
    private UserType userType;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }
    
}