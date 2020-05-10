package fsd.msservice.user.api.service;

import java.util.List;
import java.util.Optional;

import fsd.model.user.Buyer;
import fsd.msservice.user.api.exception.UserNotFoundException;

public interface BuyerService {
    
    /**
     * Regist buyer
     * @param user
     * @return
     */
    Buyer regist(Buyer user);

    /**
     * Update user
     * 
     * @param user
     * @return
     */
    Buyer update(Buyer user);

    Long count();

    /**
     * Get all buyers
     * 
     * @return
     */
    List<Buyer> findAll();

    /**
     * Find one buyer
     * 
     * @param id
     * @return
     */
    Optional<Buyer> findById(String id);

    /**
     * Find buyer based by login user name
     * 
     * @param username
     * @return
     */
    Buyer findByUsername(String username) throws UserNotFoundException;

    /**
     * delete buyer
     * 
     * @param user
     */
    void delete(Buyer user);

    /**
     * Delete the buyer by id
     * 
     * @param id
     */
    void deleteById(String id);
}