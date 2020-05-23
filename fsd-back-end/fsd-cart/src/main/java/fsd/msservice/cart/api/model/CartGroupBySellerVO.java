package fsd.msservice.cart.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartGroupBySellerVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 237512026811381629L;

	/** seller name */
	private String sellerName;

	private String available;

	/** cart products under this seller */
	private List<CartVO> products;

	public CartGroupBySellerVO(String sellerName, String available) {
		this.sellerName = sellerName;
		this.available = available;
		this.products = new ArrayList<>();
	}
}
