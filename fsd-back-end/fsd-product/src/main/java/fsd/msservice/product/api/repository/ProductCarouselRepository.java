package fsd.msservice.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.product.api.domain.Product;
import fsd.msservice.product.api.domain.ProductCarousel;

@Repository
public interface ProductCarouselRepository extends JpaRepository<ProductCarousel, Integer> {
    
    @Query("select pc from ProductCarousel pc where pc.productId = :id") 
    List<Product> findAllByProductId(@Param("id") String id);
}