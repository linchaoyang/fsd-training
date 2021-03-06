package fsd.msservice.transaction.api.controller;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fsd.common.model.transaction.NewTransaction;
import fsd.msservice.transaction.api.model.BuyerTransactionDetailVO;
import fsd.msservice.transaction.api.model.BuyerTransactionVO;
import fsd.msservice.transaction.api.model.SellerTransactionVO;
import fsd.msservice.transaction.api.service.TransactionService;

@RestController
@RequestMapping(value = "/api/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

	@Autowired
	private TransactionService service;

	/**
	 * Find all transactions under one buyer during period
	 * 
	 * @param buyerId
	 * @param start   start date with yyyyMMdd format
	 * @param end     end date with yyyyMMdd format
	 * @return
	 */
	@GetMapping("buyer/{id}")
	public List<BuyerTransactionVO> findAllUnderBuyerByPeriod(@PathVariable("id") String buyerId,
			@RequestParam(name = "start", required = false, defaultValue = "") String start,
			@RequestParam(name = "end", required = false, defaultValue = "") String end) {

		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		if (!StringUtils.isEmpty(start)) {
			try {
				format.parse(start);
			} catch (ParseException e) {

				throw new IllegalArgumentException("Parameter start not valid.");
			}
		}

		if (!StringUtils.isEmpty(end)) {
			try {
				format.parse(end);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Parameter end not valid.");
			}
		}

		return service.findAllUnderBuyerByPeriod(buyerId, start, end);
	}

	/**
	 * Delete transaction
	 * 
	 * @param carousel
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String transactionId) {
		service.deleteById(transactionId);
	}

	/**
	 * Create new transaction for one buyer
	 * 
	 * @param carousel
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<String> add(@RequestBody NewTransaction transaction) {
		String transactionId = service.add(transaction);
		return ResponseEntity.ok(MessageFormat.format("Transaction created successfully: {0}", transactionId));
	}

	/**
	 * GET all transactions under one buyer
	 * 
	 * @param buyerId
	 * @return
	 */
	@GetMapping("/{id}")
	List<BuyerTransactionDetailVO> findDetailsById(@PathVariable("id") String transactionId) {
		return service.findDetailsById(transactionId);
	}

	/**
	 * Find all transactions under one seller during period
	 * 
	 * @param sellerId
	 * @param start
	 * @param end
	 * @return
	 */
	@GetMapping("seller/{id}")
	public List<SellerTransactionVO> findAllUnderSellerByPeriod(@PathVariable("id") String sellerId,
			@RequestParam(name = "start", required = false, defaultValue = "") String start,
			@RequestParam(name = "end", required = false, defaultValue = "") String end) {

		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		if (!StringUtils.isEmpty(start)) {
			try {
				format.parse(start);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Parameter start not valid.");
			}
		}

		if (!StringUtils.isEmpty(end)) {
			try {
				format.parse(end);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Parameter end not valid.");
			}
		}

		return service.findAllUnderSellerByPeriod(sellerId, start, end);
	}

}