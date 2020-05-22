package fsd.msservice.auth.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.common.model.user.Buyer;
import fsd.msservice.auth.api.domain.AuthBuyer;

@Repository
public interface BuyerRepository extends JpaRepository<AuthBuyer, String> {

	@Query("select u from AuthBuyer u where lower(u.username) = lower(:username)")
	Buyer findByUsername(@Param("username") String username);
}