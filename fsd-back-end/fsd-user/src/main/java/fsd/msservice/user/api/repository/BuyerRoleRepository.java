package fsd.msservice.user.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.common.model.user.BuyerRole;

@Repository
public interface BuyerRoleRepository extends JpaRepository<BuyerRole, Integer> {

	/**
	 * Find role by name
	 *
	 * @param name role name
	 * @return Role
	 */
	@Query("select r from BuyerRole r where lower(r.name) = lower(:name)")
	BuyerRole findByName(@Param("name") String name);
}