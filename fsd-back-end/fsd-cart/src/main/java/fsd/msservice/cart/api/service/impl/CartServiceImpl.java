package fsd.msservice.cart.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fsd.common.model.transaction.NewTransaction;
import fsd.common.util.JpaConvertUtil;
import fsd.msservice.cart.api.entity.CartEntity;
import fsd.msservice.cart.api.model.CartGroupBySellerVO;
import fsd.msservice.cart.api.model.CartVO;
import fsd.msservice.cart.api.repository.CartRepository;
import fsd.msservice.cart.api.repository.TransactionFeignClient;
import fsd.msservice.cart.api.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository repository;

	@Autowired
	private TransactionFeignClient transactionClient;

	@Override
	public List<CartGroupBySellerVO> findAll(String buyerId) {

		List<CartGroupBySellerVO> result = new ArrayList<>();

		List<Map<String, Object>> queryResult = repository.findAllByBuyerId(buyerId);

		String sellerId = null;
		String available = null;
		CartGroupBySellerVO vo = null;
		for (Map<String, Object> record : queryResult) {
			if (!ObjectUtils.equals(record.get("available"), available)
					|| !ObjectUtils.equals(record.get("sellerId"), sellerId)) {
				sellerId = (String) record.get("sellerId");
				available = (String) record.get("available");
				vo = new CartGroupBySellerVO((String) record.get("sellerName"), available);
				result.add(vo);
			}
			CartVO cart = JpaConvertUtil.convertResult(record, CartVO.class);
			cart.setTax(cart.getPrice().multiply(cart.getGtn()).divide(BigDecimal.valueOf(100)));
			vo.getProducts().add(cart);
		}
		return result;
	}

	@Transactional
	@Override
	public void addOrUpdateCart(String buyerId, String productId, Integer stockNumber, boolean add) {

		CartEntity entity = repository.findByBuyerIdAndProductId(buyerId, productId);

		// check stock number
		if (stockNumber <= 0) {
			if (entity == null) {
				// no existing cart, then throw illegal parameter exception
				throw new fsd.common.expection.IllegalArgumentException("stockNumber");
			} else {
				if (!add) {
					// delete cart
					repository.delete(entity);
				}
				return;
			}
		}

		if (entity == null) {
			// Add new cart
			entity = new CartEntity();
			entity.setBuyerId(buyerId);
			entity.setProductId(productId);
			entity.setStockNumber(stockNumber);
		} else {
			// update cart
			if (add) {
				stockNumber = entity.getStockNumber() + stockNumber;
			}
			entity.setStockNumber(stockNumber);
		}
		repository.saveAndFlush(entity);
	}

	@Transactional
	@Override
	public String checkout(NewTransaction transaction) {
		String transactionId = transactionClient.add(transaction).getBody();

		transaction.getDetails().forEach(detail -> {
			repository.deleteByBuyerIdAndProductId(transaction.getBuyerId(), detail.getProductId());
		});

		return transactionId;
	}

	@Transactional
	@Override
	public void deleteCart(String buyerId, List<String> productIds) {

		productIds.forEach(productId -> {

			repository.deleteByBuyerIdAndProductId(buyerId, productId);
		});

	}
}
