package fsd.msservice.user.api.service;

import java.util.List;
import java.util.Optional;

import fsd.model.user.BuyerRole;
import fsd.model.user.base.RoleName;

public interface BuyerRoleService {

    Long count();

    List<BuyerRole> findAll();

    Optional<BuyerRole> findById(Integer id);

    Optional<BuyerRole> findByName(RoleName name);

    BuyerRole add(BuyerRole role);

    BuyerRole update(BuyerRole role);

    void delete(Integer id);

}