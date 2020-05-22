package fsd.msservice.user.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.common.model.user.SellerRole;

@Repository
public interface SellerRoleRepository extends JpaRepository<SellerRole, Integer> {

	/**
	 * Find role by name
	 *
	 * @param name role name
	 * @return Role
	 */
	@Query("select r from SellerRole r where lower(r.name) = lower(:name)")
	SellerRole findByName(@Param("name") String name);
}