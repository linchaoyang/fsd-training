package fsd.msservice.cart.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fsd.common.model.transaction.NewTransaction;
import fsd.msservice.cart.api.model.CartGroupBySellerVO;
import fsd.msservice.cart.api.service.CartService;

@RestController
@RequestMapping(value = "/api/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
	@Autowired
	private CartService service;

	/**
	 * List all products in my cart<BR>
	 * Group by seller
	 * 
	 * @return
	 */
	@GetMapping("")
	public List<CartGroupBySellerVO> findAll(@RequestParam("buyerId") String buyerId) {
		return service.findAll(buyerId);
	}

	@PutMapping("")
	public void addOrUpdateCart(@RequestBody Map<String, Object> cart) {
		String productId = (String) cart.get("productId");
		Integer stockNumber = (Integer) cart.get("stockNumber");
		String buyerId = (String) cart.get("buyerId");
		boolean add = "a".equals(cart.get("flag"));

		service.addOrUpdateCart(buyerId, productId, stockNumber, add);
	}

	@PostMapping("/checkout")
	public String checkout(@RequestBody NewTransaction transaction) {
		return service.checkout(transaction);
	}

	@SuppressWarnings("unchecked")
	@DeleteMapping("")
	public void deleteCart(@RequestBody Map<String, Object> cart) {
		service.deleteCart((String) cart.get("buyerId"), (List<String>) cart.get("productIds"));
	}

}
