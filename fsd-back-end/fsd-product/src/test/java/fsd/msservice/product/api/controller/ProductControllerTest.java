package fsd.msservice.product.api.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fsd.model.product.ProductSummaryVO;
import fsd.msservice.product.api.domain.Product;
import fsd.msservice.product.api.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProductControllerTest {

	@Autowired
	protected MockMvc mvc;

	@MockBean
	private ProductService service;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testFindAll() throws Exception {
		assertNotNull(this.service);

		ProductSummaryVO product = createProduct();

		List<ProductSummaryVO> products = Arrays.asList(product);

		when(service.findAll()).thenReturn(products);

		String result = mvc.perform(get("/api/product").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println(result);
		List<Product> list = mapper.readValue(result, new TypeReference<List<Product>>() {
		});
		assertTrue(list != null && list.size() == 1);
		assertEquals(product.getName(), list.get(0).getName());
	}

	private ProductSummaryVO createProduct() {
		ProductSummaryVO product = new ProductSummaryVO();
		product.setName("product name");
		product.setPrice(new BigDecimal(100.00));
		product.setImageUrl("http://xxx.com/dd.jpg");

		return product;
	}
}