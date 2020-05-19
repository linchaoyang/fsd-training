package fsd.msservice.product.api.service;

import java.util.List;

import fsd.msservice.product.api.model.SuggestCarouselVO;

public interface SuggestCarouselService {

	/**
	 * add new Suggest Carousel
	 * 
	 * @param carousel
	 * @return
	 */
	void add(SuggestCarouselVO carousel);

	/**
	 * Update SuggestCarousel carousel
	 * 
	 * @param carousel
	 * @return
	 */
	void update(SuggestCarouselVO carousel);

	/**
	 * Get all Suggest Carousel
	 * 
	 * @return
	 */
	List<SuggestCarouselVO> findAllWithinShowPeriod(String start, String end);

	/**
	 * delete carousel based on the id
	 * 
	 * @param id
	 */
	void delete(String id);

}