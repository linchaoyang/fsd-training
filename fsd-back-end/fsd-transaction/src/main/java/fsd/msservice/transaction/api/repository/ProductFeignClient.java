package fsd.msservice.transaction.api.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fsd.common.model.product.ProductDetailVO;

@FeignClient(name = "fsd-product")
public interface ProductFeignClient {

	@GetMapping(value = "/api/product/{id}")
	public ProductDetailVO findProductById(@PathVariable("id") String id);
}