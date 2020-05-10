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

import fsd.model.user.SellerRole;
import fsd.model.user.base.RoleName;
import fsd.msservice.user.api.service.SellerRoleService;

@RestController
@RequestMapping(value = "/api/seller/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class SellerRoleController {

    @Autowired
    private SellerRoleService service;

    @GetMapping()
	public List<SellerRole> findAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<SellerRole> findById(@PathVariable Integer id) {
		return service.findById(id);
	}
	
	@GetMapping("/")
	public Optional<SellerRole> findByName(@RequestParam RoleName name) {
		return service.findByName(name);
	}
	
	@PutMapping()
	public SellerRole update(@RequestBody SellerRole role) {
		return service.add(role);
	}
	
	@PostMapping()
	public SellerRole add(@RequestBody SellerRole role) {
		return service.update(role);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
    }
}