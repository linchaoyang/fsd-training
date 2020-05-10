package fsd.msservice.product.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.product.api.domain.SuggestCarousel;

@Repository
public interface SuggestCarouselRepository extends JpaRepository<SuggestCarousel, String> {
    
    @Query("select sc from SuggestCarousel sc where sc.start <= :start and :end <= sc.end") 
    List<SuggestCarousel> findAllWithinShowPeriod(@Param("start") Date start, @Param("end") Date end);

    @Modifying
    @Query("update SuggestCarousel sc where sc.productId = :productId and sc.updated = :updated") 
    SuggestCarousel update(@Param("productId") String productId, @Param("updated") Date updated);

    @Modifying
    @Query("delete SuggestCarousel sc where sc.productId = :productId and sc.updated = :updated") 
    SuggestCarousel delete(@Param("productId") String productId, @Param("updated") Date updated);
}