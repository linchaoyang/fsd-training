package fsd.msservice.transaction.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.transaction.api.domain.Product;
import fsd.msservice.transaction.api.domain.ProductCarousel;

@Repository
public interface ProductCarouselRepository extends JpaRepository<ProductCarousel, Integer> {
    
    @Query("select pc from ProductCarousel pc where pc.product.id = :id order by pc.seq asc") 
    List<Product> findAllByProductId(@Param("id") String id);

    @Modifying
    @Query(value="delete from product_Carousel pc where pc.product_id = :id", nativeQuery=true) 
    void deleteByProductId(@Param("id") String id);
}