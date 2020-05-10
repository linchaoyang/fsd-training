package fsd.msservice.auth.api.service;

import fsd.model.user.Buyer;

public interface BuyerService {

    Buyer authBuyer(String username);
}