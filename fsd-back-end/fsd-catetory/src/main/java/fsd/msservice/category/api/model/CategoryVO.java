package fsd.msservice.category.api.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9022735039486136759L;

	private Integer id;

	private String name;

	@JsonInclude(Include.NON_NULL)
	private String description;

	@JsonInclude(Include.NON_EMPTY)
	private List<SubCategoryVO> subcategories;
}
