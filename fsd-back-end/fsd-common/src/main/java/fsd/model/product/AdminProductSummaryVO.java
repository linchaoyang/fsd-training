package fsd.model.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminProductSummaryVO extends ProductSummaryVO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4730806608603955751L;

	/**
	 * product enable flag
	 * <ul>
	 * <li>0: Normal
	 * <li>1: product is locked
	 * <li>2: product is disabled
	 * </ul>
	 */
	private String status;

}