package fsd.msservice.auth.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import fsd.model.user.Buyer;
import fsd.msservice.auth.api.feign.UserFeignClient;
import fsd.msservice.auth.api.service.BuyerService;

public class BuyerServiceImpl implements BuyerService {

    @Autowired
    UserFeignClient userClient;

    @Override
    public String authBuyer(String token) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Buyer authBuyer(String name, String password) {
        // TODO Auto-generated method stub
        Buyer buyer = this.userClient.findBuyerByName(name);

        if (buyer == null) {
            return null;
        }

        
        
        return null;
    }
    
}