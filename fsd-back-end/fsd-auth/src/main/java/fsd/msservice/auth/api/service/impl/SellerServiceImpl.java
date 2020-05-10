package fsd.msservice.auth.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import fsd.msservice.auth.api.domain.AuthSeller;
import fsd.msservice.auth.api.repository.SellerRepository;
import fsd.msservice.auth.api.service.SellerService;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository repository;

    @Override
    public AuthSeller authSeller(String username) {
        AuthSeller user = new AuthSeller();
        user.setUsername(username);
        Example<AuthSeller> example = Example.of(user);
        return repository.findOne(example).get();
    }
    
}