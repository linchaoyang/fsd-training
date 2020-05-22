package fsd.msservice.product.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import fsd.common.config.WebSecurityConfig;

@SpringBootApplication
@Import({ WebSecurityConfig.class })
public class FsdProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsdProductApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	/**
	 * Initialize database for test
	 * 
	 * @param productRepository
	 * @param productCarouselRepository
	 * @param suggestCarouselRepository
	 * @return
	 */
	/*
	 * @Bean CommandLineRunner initData(ProductRepository productRepository,
	 * ProductCarouselRepository productCarouselRepository,
	 * SuggestCarouselRepository suggestCarouselRepository) { return args -> { //
	 * Role adminRole = roleRepository.findByName("ROLE_ADMIN"); // if
	 * (ObjectUtils.isEmpty(adminRole)) { // adminRole = new Role("ROLE_ADMIN"); //
	 * adminRole.setNote("Admin");s // roleRepository.saveAndFlush(adminRole);u // }
	 * List<Product> products = productRepository.findAll(); if
	 * (ObjectUtils.isEmpty(products)) {
	 * 
	 * Product product = new Product(); product.setCategoryId("ct001");
	 * product.setSubcategoryId("sct001"); product.setName("Product 1");
	 * product.setDescription("Product description"); product.setPrice(new
	 * BigDecimal(108.2)); product.setSellerId("s001"); product.setStockNumber(100);
	 * product.setStatus("0"); product.setThumbneiUrl("http://sss.com/ddd.png");
	 * 
	 * product = productRepository.save(product);
	 * 
	 * ProductCarousel carousel = new ProductCarousel(1, "http://sss.com/1.png");
	 * carousel.setProduct(product);
	 * productCarouselRepository.saveAndFlush(carousel);
	 * 
	 * List<ProductCarousel> carousels = new ArrayList<>(); carousels.add(carousel);
	 * //carousels.add(new ProductCarousel(2, "http://sss.com/2.png"));
	 * //carousels.add(new ProductCarousel(3, "http://sss.com/2.png"));
	 * 
	 * product.setCarousels(carousels);
	 * 
	 * products.add(product);
	 * 
	 * }
	 * 
	 * List<SuggestCarousel> suggestCarousels = suggestCarouselRepository.findAll();
	 * if (ObjectUtils.isEmpty(suggestCarousels)) {
	 * 
	 * SuggestCarousel suggestCarousel = new SuggestCarousel();
	 * suggestCarousel.setProductId(products.get(0).getId());
	 * suggestCarousel.setSeq(0);
	 * suggestCarousel.setImageUrl("http://ss.com/1.png");
	 * suggestCarousel.setTitle("Product 1 title");
	 * suggestCarousel.setDescription("Product 1 description");
	 * suggestCarousel.setStart(new Date());
	 * suggestCarousel.setEnd(DateUtils.addHours(new Date(), 24));
	 * suggestCarouselRepository.save(suggestCarousel); } }; }
	 */
}
