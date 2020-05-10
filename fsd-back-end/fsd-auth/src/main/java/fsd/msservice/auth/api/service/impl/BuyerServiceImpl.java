package fsd.msservice.auth.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fsd.model.user.Buyer;
import fsd.msservice.auth.api.repository.UserFeignClient;
import fsd.msservice.auth.api.service.BuyerService;

@Service
@Transactional
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    UserFeignClient userClient;

    @Override
    public Buyer authBuyer(String username) {
        // TODO Auto-generated method stub
        Buyer user = this.userClient.findBuyerByUserName(username);

        if (user == null) {
            return null;
        }

        return user;
    }
    
}