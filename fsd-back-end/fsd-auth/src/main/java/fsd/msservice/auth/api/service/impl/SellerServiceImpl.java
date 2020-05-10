package fsd.msservice.auth.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fsd.model.user.Seller;
import fsd.msservice.auth.api.repository.UserFeignClient;
import fsd.msservice.auth.api.service.SellerService;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    @Autowired
    UserFeignClient userClient;

    @Override
    public Seller authSeller(String username) {
        // TODO Auto-generated method stubUser
        Seller user = this.userClient.findSellerByUserName(username);

        if (user == null) {
            return null;
        }

        return user;
    }
    
}