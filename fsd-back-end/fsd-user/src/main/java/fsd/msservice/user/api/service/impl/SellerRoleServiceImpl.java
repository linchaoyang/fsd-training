package fsd.msservice.user.api.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import fsd.model.user.SellerRole;
import fsd.model.user.base.RoleName;
import fsd.msservice.user.api.repository.SellerRoleRepository;
import fsd.msservice.user.api.service.SellerRoleService;

public class SellerRoleServiceImpl implements SellerRoleService {

    @Autowired
    private SellerRoleRepository repository;

    public Long count() {
        return repository.count();
    }

    public List<SellerRole> findAll() {
        return repository.findAll();
    }

    public Optional<SellerRole> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<SellerRole> findByName(RoleName name) {
        SellerRole role = new SellerRole(name);
        Example<SellerRole> example = Example.of(role);
        return repository.findOne(example);
    }

    @Transactional
    public SellerRole add(SellerRole role) {
        return repository.save(role);
    }

    @Transactional
    public SellerRole update(SellerRole role) {
        return repository.save(role);
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}