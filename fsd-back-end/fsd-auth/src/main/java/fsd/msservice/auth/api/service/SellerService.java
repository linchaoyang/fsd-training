package fsd.msservice.auth.api.service;

import fsd.msservice.auth.api.domain.AuthSeller;

public interface SellerService {

    AuthSeller authSeller(String username);
}