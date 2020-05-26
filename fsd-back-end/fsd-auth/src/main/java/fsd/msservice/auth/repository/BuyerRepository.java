package fsd.msservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.auth.domain.AuthBuyer;

@Repository
public interface BuyerRepository extends JpaRepository<AuthBuyer, String> {

	@Query("select u from AuthBuyer u where u.username = :username")
	AuthBuyer findByUsername(@Param("username") String username);
}