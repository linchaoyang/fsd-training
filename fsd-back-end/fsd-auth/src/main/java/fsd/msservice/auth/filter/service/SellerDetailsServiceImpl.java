package fsd.msservice.auth.filter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fsd.model.user.Seller;
import fsd.msservice.auth.api.domain.FsdUserDetail;
import fsd.msservice.auth.api.domain.UserType;
import fsd.msservice.auth.api.service.SellerService;

@Service("sellerDetailsService")
public class SellerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SellerService sellerService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user information from user microservice
        Seller user = sellerService.authSeller(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        // Get authorities
        List<String> roles = user.getRoles().stream().map(s -> s.getName().toString()).collect(Collectors.toList());

        return new FsdUserDetail(UserType.Buyer.getType(), user.getId(), user.getUsername(), user.getPassword(), roles);
    }

}