package fsd.msservice.auth.filter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fsd.common.model.auth.FsdUserDetail;
import fsd.common.model.auth.UserType;
import fsd.msservice.auth.domain.AuthSeller;
import fsd.msservice.auth.service.UserAuthService;

@Service("sellerDetailsService")
public class SellerDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserAuthService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// get user information from user microservice
		AuthSeller user = service.authSeller(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
		// Get authorities
		String[] roles = user.getRoles().stream().map(s -> s.getName().toString()).toArray(String[]::new);
		FsdUserDetail details = new FsdUserDetail(UserType.Buyer.getType(), user.getId(), user.getUsername(),
				user.getPassword(), roles);

		details.setExiredDate(user.getExpireDate());

		return details;
	}

}