package fsd.msservice.product.api.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import fsd.msservice.product.api.domain.Product;
import fsd.msservice.product.api.domain.ProductCarousel;
import fsd.msservice.product.api.domain.SuggestCarousel;
import fsd.msservice.product.api.repository.ProductCarouselRepository;
import fsd.msservice.product.api.repository.ProductRepository;
import fsd.msservice.product.api.repository.SuggestCarouselRepository;

@Component
@ConditionalOnProperty(prefix = "job.autorun", name = "enabled", havingValue = "true", matchIfMissing = true)
public class InitDataRunner implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCarouselRepository productCarouselRepository;

    @Autowired
    private SuggestCarouselRepository suggestCarouselRepository;
    
    @Override
    public void run(String... args) throws Exception {
        List<Product> products = productRepository.findAll();
			if (ObjectUtils.isEmpty(products)) {

				Product product = new Product();
				product.setCategoryId("ct001");
				product.setSubcategoryId("sct001");
				product.setName("Product 1");
				product.setDescription("Product description");
				product.setPrice(new BigDecimal(108.2));
				product.setSellerId("s001");
				product.setStockNumber(100);
				product.setStatus("0");
				product.setThumbneiUrl("http://sss.com/ddd.png");

				product = productRepository.save(product);

				ProductCarousel carousel = new ProductCarousel(1, "http://sss.com/1.png");
				carousel.setProduct(product);
				productCarouselRepository.saveAndFlush(carousel);

				List<ProductCarousel> carousels = new ArrayList<>();
				carousels.add(carousel);
				//carousels.add(new ProductCarousel(2, "http://sss.com/2.png"));
				//carousels.add(new ProductCarousel(3, "http://sss.com/2.png"));

				product.setCarousels(carousels);

				products.add(product);

			}

			List<SuggestCarousel> suggestCarousels = suggestCarouselRepository.findAll();
			if (ObjectUtils.isEmpty(suggestCarousels)) {

				SuggestCarousel suggestCarousel = new SuggestCarousel();
				suggestCarousel.setProductId(products.get(0).getId());
				suggestCarousel.setSeq(0);
				suggestCarousel.setImageUrl("http://ss.com/1.png");
				suggestCarousel.setTitle("Product 1 title");
				suggestCarousel.setDescription("Product 1 description");
				suggestCarousel.setStart(new Date());
				suggestCarousel.setEnd(DateUtils.addHours(new Date(), 24));
				suggestCarouselRepository.save(suggestCarousel);
			}

    }
    
}