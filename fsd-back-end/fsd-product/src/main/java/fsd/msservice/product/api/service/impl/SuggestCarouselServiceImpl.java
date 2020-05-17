package fsd.msservice.product.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fsd.msservice.product.api.domain.SuggestCarousel;
import fsd.msservice.product.api.repository.SuggestCarouselRepository;
import fsd.msservice.product.api.service.SuggestCarouselService;

@Service
public class SuggestCarouselServiceImpl implements SuggestCarouselService {

	@Autowired
	private SuggestCarouselRepository repository;

	/**
	 * add new Suggest Carousel
	 * 
	 * @param carousel
	 * @return
	 */
	@Override
	@Transactional
	public SuggestCarousel add(SuggestCarousel carousel) {
		return repository.save(carousel);
	}

	/**
	 * Update SuggestCarousel carousel
	 * 
	 * @param carousel
	 * @return
	 */
	@Override
	@Transactional
	public SuggestCarousel update(SuggestCarousel carousel) {
		return repository.save(carousel);
	}

	/**
	 * Get all Suggest Carousel
	 * 
	 * @return
	 */
	@Override
	public List<SuggestCarousel> findAllWithinShowPeriod(String start, String end) {
		return repository.findAllWithinShowPeriod(start, end);
	}

	/**
	 * delete carousel
	 * 
	 * @param carousel
	 */
	@Override
	@Transactional
	public void delete(SuggestCarousel carousel) {
		repository.delete(carousel);
	}

}