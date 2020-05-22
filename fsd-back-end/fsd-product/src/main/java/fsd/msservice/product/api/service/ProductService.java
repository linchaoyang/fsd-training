package fsd.msservice.product.api.service;

import java.util.List;

import fsd.common.model.product.ProductDetailVO;
import fsd.common.model.product.ProductSummaryVO;

public interface ProductService {

	Long count();

	List<ProductSummaryVO> findAll();

	List<ProductSummaryVO> findByName(String name);

	List<ProductSummaryVO> findAllByCategoryId(String categoryId);

	ProductDetailVO findById(String id);

	void add(ProductDetailVO product);

	void update(ProductDetailVO product);

	void deleteById(String id);

}