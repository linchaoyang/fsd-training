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

import fsd.model.user.BuyerRole;
import fsd.model.user.base.RoleName;
import fsd.msservice.user.api.service.BuyerRoleService;

@RestController
@RequestMapping(value = "/api/buyer/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class BuyerRoleController {

    @Autowired
    private BuyerRoleService service;

    @GetMapping()
	public List<BuyerRole> findAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<BuyerRole> findById(@PathVariable Integer id) {
		return service.findById(id);
	}
	
	@GetMapping("/")
	public Optional<BuyerRole> findByName(@RequestParam RoleName name) {
		return service.findByName(name);
	}
	
	@PutMapping()
	public BuyerRole update(@RequestBody BuyerRole role) {
		return service.add(role);
	}
	
	@PostMapping()
	public BuyerRole add(@RequestBody BuyerRole role) {
		return service.update(role);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
    }
}