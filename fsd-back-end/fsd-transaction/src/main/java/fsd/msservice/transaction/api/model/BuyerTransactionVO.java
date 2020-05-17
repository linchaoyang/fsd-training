package fsd.msservice.transaction.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuyerTransactionVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7936424536705176482L;

	/** Transaction ID to display detail or delete this transaction */
	private String id;

	/**
	 * Image url.<br>
	 * Default use first product's image url
	 */
	private String imageUrl;

	/**
	 * Transaction title<br>
	 * Default use first product's name
	 */
	private String title;

	/**
	 * Product count purchased
	 */
	private int productCount;

	/**
	 * total money amount
	 */
	private BigDecimal totalAmount;

	/**
	 * Total tax for this transaction
	 */
	private BigDecimal totalTax;

	/** Discount code */
	private String discountCode;

	/**
	 * Transaction created time
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date created;
}
