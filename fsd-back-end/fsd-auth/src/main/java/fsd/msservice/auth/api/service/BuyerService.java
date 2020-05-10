package fsd.msservice.auth.api.service;

import fsd.msservice.auth.api.domain.AuthBuyer;

public interface BuyerService {

    AuthBuyer authBuyer(String username);
}