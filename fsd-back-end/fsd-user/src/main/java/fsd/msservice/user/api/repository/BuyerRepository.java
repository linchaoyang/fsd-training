package fsd.msservice.user.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.common.model.user.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, String> {

	@Query("select u from Buyer u where lower(u.username) = lower(:username)")
	Buyer findByUsername(@Param("username") String username);
}