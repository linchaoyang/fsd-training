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

import fsd.model.user.Buyer;
import fsd.msservice.user.api.service.BuyerService;

@RestController
@RequestMapping(value = "/api/buyer", produces = MediaType.APPLICATION_JSON_VALUE)
public class BuyerController {

    @Autowired
    private BuyerService service;

    @GetMapping()
	public List<Buyer> findAll() {
		return service.findAll();
	}
	
	/**
	 * Fing buyer based on the user id
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Optional<Buyer> findById(@PathVariable String id) {
		return service.findById(id);
	}
	
	/**
	 * Find user by login user name
	 * @param username
	 * @return
	 */
	@GetMapping("/")
	public Buyer findByUsername(@RequestParam("username") String username) {
		return service.findByUsername(username);
	}
	
	/**
	 * 增加
	 * @param user
	 * @return
	 */
	@PostMapping
	public Buyer regist(@RequestBody Buyer user) {
		return service.regist(user);
	}
 
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@PutMapping
	public Buyer update(@RequestBody Buyer user) {
		return service.update(user);
	}
	
	/**
	 * 删除指定id的用户
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable String id) {
		service.deleteById(id);
	}

}