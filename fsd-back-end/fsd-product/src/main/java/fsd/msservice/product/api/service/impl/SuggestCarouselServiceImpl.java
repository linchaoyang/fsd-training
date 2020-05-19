package fsd.msservice.product.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fsd.msservice.product.api.domain.SuggestCarousel;
import fsd.msservice.product.api.model.SuggestCarouselVO;
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
	public void add(SuggestCarouselVO carousel) {
		update(carousel);
	}

	/**
	 * Update SuggestCarousel carousel
	 * 
	 * @param carousel
	 * @return
	 */
	@Override
	@Transactional
	public void update(SuggestCarouselVO carousel) {
		SuggestCarousel entity = new SuggestCarousel();
		try {
			BeanUtils.copyProperties(entity, carousel);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		repository.save(entity);
	}

	/**
	 * Get all Suggest Carousel
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	@Override
	public List<SuggestCarouselVO> findAllWithinShowPeriod(String start, String end) {
		List<SuggestCarousel> queryResult = repository.findAllWithinShowPeriod(start, end);

		List<SuggestCarouselVO> result = new ArrayList<>();
		queryResult.forEach(entity -> {
			SuggestCarouselVO vo = new SuggestCarouselVO();
			try {
				BeanUtils.copyProperties(vo, entity);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			result.add(vo);
		});

		return result;
	}

	/**
	 * delete carousel
	 * 
	 * @param id
	 */
	@Override
	@Transactional
	public void delete(String id) {
		repository.deleteById(id);
	}

}