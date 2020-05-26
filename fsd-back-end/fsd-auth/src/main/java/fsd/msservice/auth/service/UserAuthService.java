package fsd.msservice.auth.service;

import fsd.msservice.auth.domain.AuthBuyer;
import fsd.msservice.auth.domain.AuthSeller;

public interface UserAuthService {

	AuthBuyer authBuyer(String username);

	AuthSeller authSeller(String username);
}