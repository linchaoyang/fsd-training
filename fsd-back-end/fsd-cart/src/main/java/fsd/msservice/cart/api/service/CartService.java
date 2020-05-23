package fsd.msservice.cart.api.service;

import java.util.List;

import fsd.common.model.transaction.NewTransaction;
import fsd.msservice.cart.api.model.CartGroupBySellerVO;

public interface CartService {

	List<CartGroupBySellerVO> findAll(String buyerId);

	void addOrUpdateCart(String buyerId, String productId, Integer stockNumber, boolean add);

	String checkout(NewTransaction transaction);

	void deleteCart(String buyerId, List<String> productIds);
}
