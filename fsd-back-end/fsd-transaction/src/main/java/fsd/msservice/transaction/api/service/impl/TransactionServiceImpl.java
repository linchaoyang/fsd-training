package fsd.msservice.transaction.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fsd.expection.ProductNotFoundException;
import fsd.model.product.ProductSummaryVO;
import fsd.model.user.Buyer;
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
	public List<BuyerTransactionVO> findAllByBuyer(String buyerId) {
		return repository.findAllByBuyer(buyerId);
	}

	@Override
	public List<BuyerTransactionVO> findAllUnderBuyerByPeriod(String buyerId, String start, String end) {
		return repository.findAllUnderBuyerByPeriod(buyerId, start, end);
	}

	@Override
	@Transactional
	public void add(NewTransaction transaction) {

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
			// seller id
			detailEntity.setSellerId(detail.getSellerId());

			// set information from product summary vo from other ms-service
			// product name
			detailEntity.setProductName(productSummaryVO.getName());
			// seller name
			detailEntity.setSellerName(productSummaryVO.getSellerName());
			// image url
			detailEntity.setImagUrl(productSummaryVO.getImageUrl());
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
		return detailRepository.findDetailsById(transactionId);
	}

	@Override
	public List<SellerTransactionVO> findAllBySeller(String sellerId) {

		return repository.findAllBySeller(sellerId);
	}

	@Override
	public List<SellerTransactionVO> findAllUnderSellerByPeriod(String sellerId, String start, String end) {
		return repository.findAllUnderSellerByPeriod(sellerId, start, end);
	}

}