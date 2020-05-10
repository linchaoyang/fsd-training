package fsd.msservice.auth.api.service;

import fsd.model.user.Seller;

public interface SellerService {

    Seller authSeller(String username);
}