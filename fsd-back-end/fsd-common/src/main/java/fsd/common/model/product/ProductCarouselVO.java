package fsd.model.product;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCarouselVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 871717183575312114L;
	/** Image url */
	private String imageUrl;
}
