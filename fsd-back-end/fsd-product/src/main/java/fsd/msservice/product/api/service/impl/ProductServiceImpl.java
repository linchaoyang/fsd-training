package fsd.msservice.product.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import fsd.expection.ProductNotFoundException;
import fsd.model.product.ProductCarouselVO;
import fsd.model.product.ProductDetailVO;
import fsd.model.product.ProductSummaryVO;
import fsd.msservice.product.api.domain.Product;
import fsd.msservice.product.api.domain.ProductCarousel;
import fsd.msservice.product.api.repository.ProductCarouselRepository;
import fsd.msservice.product.api.repository.ProductRepository;
import fsd.msservice.product.api.service.ProductService;
import fsd.util.JpaConvertUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductCarouselRepository carouselRepository;

	@Override
	public Long count() {
		return repository.count();
	}

	/**
	 * Get all products
	 * 
	 * @return
	 */
	@Override
	public List<ProductSummaryVO> findAll() {
		return JpaConvertUtil.convertResult(repository.findProductsSummary("", ""), ProductSummaryVO.class);
	}

	/**
	 * Find all products matched the name
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public List<ProductSummaryVO> findByName(String name) {
		return JpaConvertUtil.convertResult(repository.findProductsSummary(name, ""), ProductSummaryVO.class);
	}

	/**
	 * Find all products undr one catetory
	 * 
	 * @param categoryId
	 * @return
	 */
	@Override
	public List<ProductSummaryVO> findAllByCategoryId(String categoryId) {
		return JpaConvertUtil.convertResult(repository.findProductsSummary("", categoryId), ProductSummaryVO.class);
	}

	/**
	 * Find one product
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public ProductDetailVO findById(String id) {

		List<Map<String, Object>> queryResult = repository.findProductDetail(id);

		ProductDetailVO result = null;

		if (ObjectUtils.isEmpty(queryResult)) {
			return result;
		}

		result = JpaConvertUtil.convertResult(queryResult.get(0), ProductDetailVO.class);
		List<ProductCarouselVO> carousels = new ArrayList<>();
		result.setCarousels(carousels);
		queryResult.forEach(record -> {

			ProductCarouselVO carousel = JpaConvertUtil.convertResult(record, ProductCarouselVO.class);
			carousels.add(carousel);

		});

		return result;
	}

	/**
	 * Add new product
	 * 
	 * @param product
	 * @return
	 */
	@Override
	@Transactional
	public void add(ProductDetailVO product) {

		Product productEntity = new Product();
		try {
			BeanUtils.copyProperties(productEntity, product);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		productEntity = repository.save(productEntity);

		saveCarousels(product, productEntity);
	}

	/**
	 * Update one product
	 * 
	 * @param product
	 * @return
	 */
	@Override
	@Transactional
	public void update(ProductDetailVO product) {
		if (product.getId() != null) {
			Product productEntity = repository.findById(product.getId()).orElse(null);

			if (productEntity == null) {
				throw new ProductNotFoundException("Product not found with id: %s", product.getId());
			}

			// delete carousels under this product
			if (ObjectUtils.isEmpty(product.getCarousels())) {
				carouselRepository.deleteByProductId(product.getId());
			}

			saveCarousels(product, productEntity);

			repository.save(productEntity);
		} else {
			add(product);
		}

	}

	private void saveCarousels(ProductDetailVO product, Product productEntity) {

		List<ProductCarousel> carouselEntities = new ArrayList<>();

		if (!ObjectUtils.isEmpty(product.getCarousels())) {
			int seq = 0;
			for (ProductCarouselVO carousel : product.getCarousels()) {
				ProductCarousel carouselEntity = new ProductCarousel();
				carouselEntity.setImageUrl(carousel.getImageUrl());
				carouselEntity.setSeq(++seq);
				carouselEntity.setProduct(productEntity);
				carouselEntity = carouselRepository.save(carouselEntity);

				carouselEntities.add(carouselEntity);
			}
		}

		productEntity.setCarousels(carouselEntities);
	}

	/**
	 * Delete the product by id
	 * 
	 * @param id
	 */
	@Override
	@Transactional
	public void deleteById(String id) {
//		Optional<Product> product = repository.findById(id);
//		if (product.isPresent()) {
//			carouselRepository.deleteByProductId(id);
//			repository.deleteById(id);
//		}
		// delete the main table will also delete the carouse table by using cascade
		// when creating table
		repository.deleteById(id);
	}

}