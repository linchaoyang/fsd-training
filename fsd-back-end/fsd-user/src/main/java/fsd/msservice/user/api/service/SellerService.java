package fsd.msservice.user.api.service;

import java.util.List;
import java.util.Optional;

import fsd.common.model.user.Seller;
import fsd.msservice.user.api.exception.UserNotFoundException;

public interface SellerService {

	/**
	 * Regist Seller
	 * 
	 * @param user
	 * @return
	 */
	Seller regist(Seller user);

	/**
	 * Update user
	 * 
	 * @param user
	 * @return
	 */
	Seller update(Seller user);

	Long count();

	/**
	 * Get all Sellers
	 * 
	 * @return
	 */
	List<Seller> findAll();

	/**
	 * Find one Seller
	 * 
	 * @param id
	 * @return
	 */
	Optional<Seller> findById(String id);

	/**
	 * Find Seller based by login user name
	 * 
	 * @param username
	 * @return
	 */
	Seller findByUsername(String username) throws UserNotFoundException;

	/**
	 * delete Seller
	 * 
	 * @param user
	 */
	void delete(Seller user);

	/**
	 * Delete the Seller by id
	 * 
	 * @param id
	 */
	void deleteById(String id);
}