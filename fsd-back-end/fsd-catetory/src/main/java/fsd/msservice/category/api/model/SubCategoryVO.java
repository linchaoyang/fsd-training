package fsd.msservice.category.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubCategoryVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 237512026811381629L;

	private Integer id;

	private String name;

	private BigDecimal gtn;

	@JsonInclude(Include.NON_NULL)
	private String description;
}
