package fsd.msservice.user.api.service;

import java.util.List;
import java.util.Optional;

import fsd.model.user.SellerRole;
import fsd.model.user.base.RoleName;

public interface SellerRoleService {

    Long count();

    List<SellerRole> findAll();

    Optional<SellerRole> findById(Integer id);

    Optional<SellerRole> findByName(RoleName name);

    SellerRole add(SellerRole role);

    SellerRole update(SellerRole role);

    void delete(Integer id);

}