package fsd.msservice.transaction.api.service;

import java.util.List;

import fsd.msservice.transaction.api.model.BuyerTransactionDetailVO;
import fsd.msservice.transaction.api.model.BuyerTransactionVO;
import fsd.msservice.transaction.api.model.NewTransaction;
import fsd.msservice.transaction.api.model.SellerTransactionVO;

public interface TransactionService {

//	/**
//	 * Find all transactions under one buyer
//	 * 
//	 * @param buyerId
//	 * @return
//	 */
//	List<BuyerTransactionVO> findAllByBuyer(String buyerId);

	/**
	 * Find all transactions under one buyer during period
	 * 
	 * @param buyerId
	 * @param start
	 * @param end
	 * @return
	 */
	List<BuyerTransactionVO> findAllUnderBuyerByPeriod(String buyerId, String start, String end);

	/**
	 * Delete one transaction by transaction id under one buyer
	 * 
	 * @param transactionId
	 */
	void deleteById(String transactionId);

	/**
	 * Add new transaction under one buyer
	 * 
	 * @param transaction
	 */
	void add(NewTransaction transaction);

	/**
	 * Find transaction details
	 * 
	 * @param transactionId
	 * @return
	 */
	List<BuyerTransactionDetailVO> findDetailsById(String transactionId);

//	/**
//	 * Find all transactions under one seller
//	 * 
//	 * @param sellerId
//	 * @return
//	 */
//	List<SellerTransactionVO> findAllBySeller(String sellerId);

	/**
	 * Find all transactions under one seller during period
	 * 
	 * @param sellerId
	 * @param start
	 * @param end
	 * @return
	 */
	List<SellerTransactionVO> findAllUnderSellerByPeriod(String sellerId, String start, String end);

}