package fsd.msservice.transaction.api.service;

import java.util.Date;
import java.util.List;

import fsd.msservice.transaction.api.domain.SuggestCarousel;

public interface SuggestCarouselService {
    
    /**
     * add new Suggest Carousel
     * @param carousel
     * @return
     */
    SuggestCarousel add(SuggestCarousel carousel);

    /**
     * Update SuggestCarousel carousel
     * 
     * @param carousel
     * @return
     */
    SuggestCarousel update(SuggestCarousel carousel);

    /**
     * Get all Suggest Carousel
     * 
     * @return
     */
    List<SuggestCarousel> findAllWithinShowPeriod(String start, String end);

    /**
     * delete carousel
     * 
     * @param carousel
     */
    void delete(SuggestCarousel carousel);

}