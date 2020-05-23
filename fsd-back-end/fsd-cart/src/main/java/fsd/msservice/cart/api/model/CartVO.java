package fsd.msservice.cart.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9022735039486136759L;

	/** product id */
	private String productId;

	/** product image url */
	private String imageUrl;

	/**
	 * product name
	 */
	private String name;

	/**
	 * product price
	 */
	private BigDecimal price;

	/**
	 * product GTN
	 */
	private BigDecimal gtn;

	/**
	 * product tax
	 */
	private BigDecimal tax;

	/** stock number */
	private Integer stockNumber;

}
