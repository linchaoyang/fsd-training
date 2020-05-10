package fsd.msservice.auth.filter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fsd.msservice.auth.api.domain.AuthBuyer;
import fsd.msservice.auth.api.domain.FsdUserDetail;
import fsd.msservice.auth.api.domain.UserType;
import fsd.msservice.auth.api.service.BuyerService;

@Service("buyerDetailsService")
public class BuyerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BuyerService service;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user information from user microservice
        AuthBuyer user = service.authBuyer(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        // Get authorities
        List<String> roles = user.getRoles().stream().map(r -> r.getName().toString()).collect(Collectors.toList());

        return new FsdUserDetail(UserType.Buyer.getType(), user.getId(), user.getUsername(), user.getPassword(), roles);
    }

}