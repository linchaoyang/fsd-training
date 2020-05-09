package fsd.msservice.auth.api.service;

import fsd.model.user.Buyer;

public interface BuyerService {

    String authBuyer(String token);

    Buyer authBuyer(String name, String password);
}