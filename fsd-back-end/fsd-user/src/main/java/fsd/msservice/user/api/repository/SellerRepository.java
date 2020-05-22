package fsd.msservice.user.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.common.model.user.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {

	@Query("select u from Seller u where lower(u.username) = lower(:username)")
	Seller findByUsername(@Param("username") String username);
}