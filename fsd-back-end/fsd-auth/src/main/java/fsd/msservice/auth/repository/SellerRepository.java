package fsd.msservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.auth.domain.AuthSeller;

@Repository
public interface SellerRepository extends JpaRepository<AuthSeller, String> {

	@Query("select u from AuthSeller u where u.username = :username")
	AuthSeller findByUsername(@Param("username") String username);
}