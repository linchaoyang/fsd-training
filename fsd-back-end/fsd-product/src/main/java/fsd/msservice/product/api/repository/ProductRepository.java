package fsd.msservice.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.product.api.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    @Query("select p from Product p where p.name like concat('%', :name, '%')") 
    List<Product> findByProductName(@Param("name") String name);

    @Query("select p from Product p where p.categoryId = :id") 
    List<Product> findByCategoryId(@Param("id") String id);
}