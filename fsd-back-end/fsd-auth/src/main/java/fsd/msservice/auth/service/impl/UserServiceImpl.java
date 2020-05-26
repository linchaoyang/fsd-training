package fsd.msservice.auth.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fsd.msservice.auth.domain.AuthBuyer;
import fsd.msservice.auth.domain.AuthSeller;
import fsd.msservice.auth.repository.BuyerRepository;
import fsd.msservice.auth.repository.SellerRepository;
import fsd.msservice.auth.service.UserAuthService;

@Service
@Transactional
public class UserServiceImpl implements UserAuthService {

	@Autowired
	BuyerRepository buyerRepository;

	@Autowired
	SellerRepository sellerRepository;

	@Override
	public AuthBuyer authBuyer(String username) {

//        AuthBuyer user = new AuthBuyer();
//        user.setUsername(username);
//        Example<AuthBuyer> example = Example.of(user);
		return buyerRepository.findByUsername(username);

		// Buyer user = this.userClient.findBuyerByUserName(username);
		// return user;
	}

	@Override
	public AuthSeller authSeller(String username) {
		return sellerRepository.findByUsername(username);
	}

}