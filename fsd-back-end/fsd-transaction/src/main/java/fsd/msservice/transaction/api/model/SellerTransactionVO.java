package fsd.msservice.transaction.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SellerTransactionVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7936424536705176482L;

	/** Transaction ID */
	private String id;

	/** Buyer name */
	private String buyerName;

	/**
	 * Buyer email
	 */
	private String email;

	/**
	 * Buyer mobile
	 */
	private String mobile;

	/**
	 * Product id
	 */
	private String productId;

	/**
	 * Product name
	 */
	private String productName;

	/**
	 * Product price
	 */
	private BigDecimal price;

	/**
	 * purchase count
	 */
	private int purchaseCount;

	/**
	 * product money amount
	 */
	private BigDecimal totalAmount;

	/**
	 * Total tax for this product
	 */
	private BigDecimal totalTax;

	/**
	 * Created time
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date created;

	/**
	 * Remark
	 */
	private String remarks;
}
