package fsd.msservice.auth.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import fsd.msservice.auth.api.domain.AuthBuyer;
import fsd.msservice.auth.api.repository.BuyerRepository;
import fsd.msservice.auth.api.service.BuyerService;

@Service
@Transactional
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    BuyerRepository repository;

    @Override
    public AuthBuyer authBuyer(String username) {

        AuthBuyer user = new AuthBuyer();
        user.setUsername(username);
        Example<AuthBuyer> example = Example.of(user);
        return repository.findOne(example).get();

        //Buyer user = this.userClient.findBuyerByUserName(username);
        //return user;
    }
    
}