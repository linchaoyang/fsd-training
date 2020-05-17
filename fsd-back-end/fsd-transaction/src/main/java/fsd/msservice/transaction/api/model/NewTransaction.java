package fsd.msservice.transaction.api.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewTransaction implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3850376600371650820L;

	/** Discount code */
	private String discountCode;

	/** Buyer id */
	private String buyerId;

	/**
	 * Remark
	 */
	private String remarks;

	/** Transaction details */
	private List<NewTransactionDetail> details;
}
