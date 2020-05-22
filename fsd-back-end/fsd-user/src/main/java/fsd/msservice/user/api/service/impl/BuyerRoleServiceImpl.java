package fsd.msservice.user.api.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import fsd.common.model.user.BuyerRole;
import fsd.common.model.user.base.RoleName;
import fsd.msservice.user.api.repository.BuyerRoleRepository;
import fsd.msservice.user.api.service.BuyerRoleService;

@Service
public class BuyerRoleServiceImpl implements BuyerRoleService {

	@Autowired
	private BuyerRoleRepository repository;

	@Override
	public Long count() {
		return repository.count();
	}

	@Override
	public List<BuyerRole> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<BuyerRole> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Optional<BuyerRole> findByName(RoleName name) {
		BuyerRole role = new BuyerRole(name);
		Example<BuyerRole> example = Example.of(role);
		return repository.findOne(example);
	}

	@Override
	@Transactional
	public BuyerRole add(BuyerRole role) {
		return repository.save(role);
	}

	@Override
	@Transactional
	public BuyerRole update(BuyerRole role) {
		return repository.save(role);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		repository.deleteById(id);
	}
}