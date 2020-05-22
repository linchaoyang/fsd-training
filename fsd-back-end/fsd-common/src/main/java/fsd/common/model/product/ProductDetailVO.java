package fsd.common.model.product;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDetailVO extends ProductSummaryVO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 128930204950645232L;

	/**
	 * category id
	 */
	private String categoryId;

	private String categoryName;

	/**
	 * subcatetory id
	 */
	private String subcategoryId;

	/**
	 * subcatetory Name
	 */
	private String subcategoryName;
	/**
	 * description
	 * </p>
	 */
	private String description;

	/**
	 * Stock number
	 */
	private Integer stockNumber;

	/**
	 * Remark
	 */
	private String remarks;

	/** Carousels */
	private List<ProductCarouselVO> carousels;

}