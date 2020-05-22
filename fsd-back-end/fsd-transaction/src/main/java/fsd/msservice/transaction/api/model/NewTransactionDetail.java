package fsd.msservice.transaction.api.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewTransactionDetail implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3850376600371650820L;

	/** Product id */
	private String productId;

	/**
	 * Purchased stock number for this product
	 */
	private int stock;
}
