package fsd.msservice.user.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fsd.common.model.user.Buyer;
import fsd.common.model.user.BuyerRole;
import fsd.msservice.user.api.exception.UserNotFoundException;
import fsd.msservice.user.api.repository.BuyerRepository;
import fsd.msservice.user.api.service.BuyerService;

@Service
// @Transactional
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private BuyerRepository repository;

	/**
	 * Load BCryptPasswordEncoder
	 */
	@Autowired
	private PasswordEncoder encoder;

	/**
	 * Regist buyer
	 * 
	 * @param user
	 * @return
	 */
	@Override
	@Transactional
	public Buyer regist(Buyer user) {
		// if no roles data, then set the default buyer role
		if (user.getRoles() == null || user.getRoles().size() == 0) {
			BuyerRole buyerRole = new BuyerRole();
			List<BuyerRole> roles = new ArrayList<>();
			roles.add(buyerRole);
			user.setRoles(roles);
		}
		user.setPassword(encoder.encode(user.getPassword()));
		return repository.save(user);
	}

	/**
	 * Update user
	 * 
	 * @param user
	 * @return
	 */
	@Override
	@Transactional
	public Buyer update(Buyer user) {
		return repository.save(user);
	}

	@Override
	public Long count() {
		return repository.count();
	}

	/**
	 * Get all buyers
	 * 
	 * @return
	 */
	@Override
	public List<Buyer> findAll() {
		return repository.findAll();
	}

	/**
	 * Find one buyer
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Optional<Buyer> findById(String id) {
		return repository.findById(id);
	}

	/**
	 * Find buyer based by login user name
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public Buyer findByUsername(String username) throws UserNotFoundException {
		Buyer user = new Buyer();
		user.setUsername(username);
		Example<Buyer> example = Example.of(user);
		return repository.findOne(example).orElseThrow(() -> new UserNotFoundException(username));
	}

	/**
	 * delete buyer
	 * 
	 * @param user
	 */
	@Override
	@Transactional
	public void delete(Buyer user) {
		repository.delete(user);
	}

	/**
	 * Delete the buyer by id
	 * 
	 * @param id
	 */
	@Override
	@Transactional
	public void deleteById(String id) {
		repository.deleteById(id);
	}

}