package fsd.msservice.user.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fsd.common.model.user.Seller;
import fsd.common.model.user.SellerRole;
import fsd.msservice.user.api.exception.UserNotFoundException;
import fsd.msservice.user.api.repository.SellerRepository;
import fsd.msservice.user.api.service.SellerService;

@Service
// @Transactional
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerRepository repository;

	/**
	 * Regist Seller
	 * 
	 * @param user
	 * @return
	 */
	@Override
	@Transactional
	public Seller regist(Seller user) {
		// if no roles data, then set the default Seller role
		if (user.getRoles() == null || user.getRoles().size() == 0) {
			SellerRole SellerRole = new SellerRole();
			List<SellerRole> roles = new ArrayList<>();
			roles.add(SellerRole);
			user.setRoles(roles);
		}
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
	public Seller update(Seller user) {
		return repository.save(user);
	}

	@Override
	public Long count() {
		return repository.count();
	}

	/**
	 * Get all Sellers
	 * 
	 * @return
	 */
	@Override
	public List<Seller> findAll() {
		return repository.findAll();
	}

	/**
	 * Find one Seller
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Optional<Seller> findById(String id) {
		return repository.findById(id);
	}

	/**
	 * Find Seller based by login user name
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public Seller findByUsername(String username) throws UserNotFoundException {
		Seller user = new Seller();
		user.setUsername(username);
		Example<Seller> example = Example.of(user);
		return repository.findOne(example).orElseThrow(() -> new UserNotFoundException(username));
	}

	/**
	 * delete Seller
	 * 
	 * @param user
	 */
	@Override
	@Transactional
	public void delete(Seller user) {
		repository.delete(user);
	}

	/**
	 * Delete the Seller by id
	 * 
	 * @param id
	 */
	@Override
	@Transactional
	public void deleteById(String id) {
		repository.deleteById(id);
	}

}