package fsd.msservice.transaction.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.transaction.api.domain.SuggestCarousel;

@Repository
public interface SuggestCarouselRepository extends JpaRepository<SuggestCarousel, String> {
    
    @Query("select sc from SuggestCarousel sc where DATE_FORMAT(sc.start,'%Y%m%d%H%i%S') >= :start and :end >= DATE_FORMAT(sc.end,'%Y%m%d%H%i%S')") 
    List<SuggestCarousel> findAllWithinShowPeriod(@Param("start") String start, @Param("end") String end);

    // @Modifying
    // @Query("update SuggestCarousel sc set sc. where sc.productId = :productId and sc.updated = :updated") 
    // SuggestCarousel update(@Param("productId") String productId, @Param("updated") Date updated);

    @Modifying
    @Query("delete from SuggestCarousel sc where sc.productId = :productId and sc.updated = :updated") 
    SuggestCarousel delete(@Param("productId") String productId, @Param("updated") Date updated);
}