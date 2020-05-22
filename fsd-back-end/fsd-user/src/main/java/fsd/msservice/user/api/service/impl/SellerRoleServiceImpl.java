package fsd.msservice.user.api.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import fsd.common.model.user.SellerRole;
import fsd.common.model.user.base.RoleName;
import fsd.msservice.user.api.repository.SellerRoleRepository;
import fsd.msservice.user.api.service.SellerRoleService;

@Service
public class SellerRoleServiceImpl implements SellerRoleService {

	@Autowired
	private SellerRoleRepository repository;

	@Override
	public Long count() {
		return repository.count();
	}

	@Override
	public List<SellerRole> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<SellerRole> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Optional<SellerRole> findByName(RoleName name) {
		SellerRole role = new SellerRole(name);
		Example<SellerRole> example = Example.of(role);
		return repository.findOne(example);
	}

	@Override
	@Transactional
	public SellerRole add(SellerRole role) {
		return repository.save(role);
	}

	@Override
	@Transactional
	public SellerRole update(SellerRole role) {
		return repository.save(role);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		repository.deleteById(id);
	}
}