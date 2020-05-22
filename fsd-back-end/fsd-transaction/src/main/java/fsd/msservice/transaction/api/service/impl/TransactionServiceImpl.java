package fsd.msservice.transaction.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fsd.common.expection.ProductNotFoundException;
import fsd.common.model.product.ProductSummaryVO;
import fsd.common.model.user.Buyer;
import fsd.common.util.JpaConvertUtil;
import fsd.msservice.transaction.api.entity.TransactionDetailEntity;
import fsd.msservice.transaction.api.entity.TransactionEntity;
import fsd.msservice.transaction.api.model.BuyerTransactionDetailVO;
import fsd.msservice.transaction.api.model.BuyerTransactionVO;
import fsd.msservice.transaction.api.model.NewTransaction;
import fsd.msservice.transaction.api.model.NewTransactionDetail;
import fsd.msservice.transaction.api.model.SellerTransactionVO;
import fsd.msservice.transaction.api.repository.ProductFeignClient;
import fsd.msservice.transaction.api.repository.TransactionDetailRepository;
import fsd.msservice.transaction.api.repository.TransactionRepository;
import fsd.msservice.transaction.api.repository.UserFeignClient;
import fsd.msservice.transaction.api.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository repository;

	@Autowired
	private TransactionDetailRepository detailRepository;

	@Autowired
	private ObjectProvider<ProductFeignClient> productClient;

	@Autowired
	private ObjectProvider<UserFeignClient> userClient;

	@Override
	public List<BuyerTransactionVO> findAllUnderBuyerByPeriod(String buyerId, String start, String end) {
		return JpaConvertUtil.convertResult(repository.findAllUnderBuyerByPeriod(buyerId, start, end),
				BuyerTransactionVO.class);
	}

	@Override
	@Transactional
	public String add(NewTransaction transaction) {

		// TODO check discount code whether is correct

		List<TransactionDetailEntity> details = new ArrayList<>();

		BigDecimal totalTransactionTax = BigDecimal.ZERO;
		BigDecimal totalTransactionAmount = BigDecimal.ZERO;
		int index = 0;
		for (NewTransactionDetail detail : transaction.getDetails()) {

			index++;
			// get product summary information from product service
			ProductSummaryVO productSummaryVO = productClient.getObject().findProductById(detail.getProductId());
			if (productSummaryVO == null) {
				throw new ProductNotFoundException("Product of %d not found.", index);
			}

			TransactionDetailEntity detailEntity = new TransactionDetailEntity();
			// set information from transaction detail came from client
			// seq
			detailEntity.setSeq(String.valueOf(index - 1));
			// product id
			detailEntity.setProductId(detail.getProductId());
			// purchase stock number
			detailEntity.setStock(detail.getStock());

			// set information from product summary vo from other ms-service
			// seller id
			detailEntity.setSellerId(productSummaryVO.getSellerId());
			// seller name
			detailEntity.setSellerName(productSummaryVO.getSellerName());
			// product name
			detailEntity.setProductName(productSummaryVO.getName());
			// image url
			detailEntity.setImageUrl(productSummaryVO.getImageUrl());
			// price
			detailEntity.setPrice(productSummaryVO.getPrice());
			// total tax
			BigDecimal totalTax = productSummaryVO.getPrice()
					.multiply(productSummaryVO.getTax().divide(BigDecimal.valueOf(100)))
					.multiply(BigDecimal.valueOf(detail.getStock()));
			detailEntity.setTotalTax(totalTax);
			totalTransactionTax = totalTransactionTax.add(totalTax);
			// total amount
			BigDecimal totalAmount = productSummaryVO.getPrice().multiply(BigDecimal.valueOf(detail.getStock()))
					.add(totalTax);
			detailEntity.setTotalAmount(totalAmount);
			totalTransactionAmount = totalTransactionAmount.add(totalAmount);

			// Add to transaction detail list
			details.add(detailEntity);
		}

		TransactionEntity transactionEntity = new TransactionEntity();
		transactionEntity.setBuyerId(transaction.getBuyerId());
		Buyer buyer = userClient.getObject().findBuyerById(transaction.getBuyerId());
		transactionEntity.setBuyerName(buyer.getUsername());
		transactionEntity.setMobile(buyer.getMobile());
		transactionEntity.setEmail(buyer.getEmail());
		transactionEntity.setDiscountCode(transaction.getDiscountCode());
		transactionEntity.setRemarks(transaction.getRemarks());
		transactionEntity.setTotalTax(totalTransactionTax);
		transactionEntity.setTotalAmount(totalTransactionAmount);
		transactionEntity.setStatus("0");

		// Insert transaction into database
		transactionEntity = repository.saveAndFlush(transactionEntity);

		// Insert detail into database
		for (TransactionDetailEntity entity : details) {

			entity.setTransaction(transactionEntity);

			detailRepository.save(entity);
		}

		return transactionEntity.getId();

	}

	/**
	 * Delete one transaction by transaction id under one buyer
	 * 
	 * @param transactionId
	 */
	@Override
	@Transactional
	public void deleteById(String transactionId) {
		repository.deleteById(transactionId);
	}

	@Override
	public List<BuyerTransactionDetailVO> findDetailsById(String transactionId) {
		return JpaConvertUtil.convertResult(detailRepository.findDetailsById(transactionId),
				BuyerTransactionDetailVO.class);
	}

	@Override
	public List<SellerTransactionVO> findAllUnderSellerByPeriod(String sellerId, String start, String end) {
		return JpaConvertUtil.convertResult(repository.findAllUnderSellerByPeriod(sellerId, start, end),
				SellerTransactionVO.class);
	}
}