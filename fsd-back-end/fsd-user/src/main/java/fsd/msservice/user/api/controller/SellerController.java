package fsd.msservice.user.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fsd.model.user.Seller;
import fsd.msservice.user.api.service.SellerService;

@RestController
@RequestMapping(value = "/api/seller", produces = MediaType.APPLICATION_JSON_VALUE)
public class SellerController {

    @Autowired
    private SellerService service;

    @GetMapping()
	public List<Seller> findAll() {
		return service.findAll();
	}
	
	/**
	 * Fing seller based on the user id
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Optional<Seller> findById(@PathVariable String id) {
		return service.findById(id);
	}
	
	/**
	 * Find seller by login user name
	 * @param username
	 * @return
	 */
	@GetMapping("/")
	public Seller findByUsername(@RequestParam("username") String username) {
		return service.findByUsername(username);
	}
	
	/**
	 * Regist new seller
	 * @param user
	 * @return
	 */
	@PostMapping
	public Seller regist(@RequestBody Seller user) {
		return service.regist(user);
	}
 
	/**
	 * Update seller information
	 * @param user
	 * @return
	 */
	@PutMapping
	public Seller update(@RequestBody Seller user) {
		return service.update(user);
	}
	
	/**
	 * Delete seller based on the id
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable String id) {
		service.deleteById(id);
	}

}