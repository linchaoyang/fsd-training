package fsd.msservice.auth.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.model.user.Seller;
import fsd.msservice.auth.api.domain.AuthSeller;

@Repository
public interface SellerRepository extends JpaRepository<AuthSeller, String> {
    
    @Query("select u from AuthSeller u where lower(u.username) = lower(:username)") 
    Seller findByUsername(@Param("username") String username);
}