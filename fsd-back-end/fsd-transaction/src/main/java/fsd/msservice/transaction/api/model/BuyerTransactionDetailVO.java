package fsd.msservice.transaction.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transaction detail that demonstrates how many products were purchased.
 * 
 * @author LinChaoYang
 *
 */
@Data
@NoArgsConstructor
public class BuyerTransactionDetailVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3258560721657969246L;

	/**
	 * Product id contained in this transaction
	 */
	private String productId;

	/**
	 * Image url
	 */
	private String imagUrl;

	/**
	 * Product name
	 */
	private String productName;

	/**
	 * Purchased stock number for this product
	 */
	private int stock;

	/**
	 * Purchased total money amount
	 */
	private BigDecimal totalAmount;

	/**
	 * Total tax for this product
	 */
	private BigDecimal totalTax;

	/** Seller id */
	private String sellerId;

	/** Seller name */
	private String sellerName;

}