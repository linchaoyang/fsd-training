package fsd.msservice.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fsd.model.product.ProductDetailVO;
import fsd.model.product.ProductSummaryVO;
import fsd.msservice.product.api.service.ProductService;

@RestController
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping()
	public List<ProductSummaryVO> findAll() {
		return service.findAll();
	}

	@GetMapping("/category/{id}")
	public List<ProductSummaryVO> findAllByCategoryId(@PathVariable String id) {
		return service.findAllByCategoryId(id);
	}

	/**
	 * Fing product based on the product id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ProductDetailVO findById(@PathVariable String id) {
		return service.findById(id);
	}

	/**
	 * Find product by name
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/")
	public List<ProductSummaryVO> findByName(@RequestParam("name") String name) {
		return service.findByName(name);
	}

	/**
	 * Add new product
	 * 
	 * @param product
	 * @return
	 */
	@PostMapping
	public void add(@RequestBody ProductDetailVO product) {
		service.add(product);
	}

	/**
	 * Update product information
	 * 
	 * @param product
	 * @return
	 */
	@PutMapping
	public void update(@RequestBody ProductDetailVO product) {
		service.update(product);
	}

	/**
	 * Delete product based on the id
	 * 
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable String id) {
		service.deleteById(id);
	}
}