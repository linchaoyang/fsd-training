package fsd.msservice.user.api.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import fsd.model.user.BuyerRole;
import fsd.model.user.base.RoleName;
import fsd.msservice.user.api.repository.BuyerRoleRepository;
import fsd.msservice.user.api.service.BuyerRoleService;

public class BuyerRoleServiceImpl implements BuyerRoleService {

    @Autowired
    private BuyerRoleRepository repository;

    public Long count() {
        return repository.count();
    }

    public List<BuyerRole> findAll() {
        return repository.findAll();
    }

    public Optional<BuyerRole> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<BuyerRole> findByName(RoleName name) {
        BuyerRole role = new BuyerRole(name);
        Example<BuyerRole> example = Example.of(role);
        return repository.findOne(example);
    }

    @Transactional
    public BuyerRole add(BuyerRole role) {
        return repository.save(role);
    }

    @Transactional
    public BuyerRole update(BuyerRole role) {
        return repository.save(role);
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}