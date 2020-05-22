package fsd.msservice.category.api.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SubCategory Entity.
 * 
 * @author LinChaoYang
 *
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "subcategory")
@EntityListeners(AuditingEntityListener.class)
public class SubCategoryEntity {

	/**
	 * SubCategory ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * subcategory name
	 */
	@Column(name = "name", columnDefinition = "varchar(200) not null comment 'subcategory name'")
	private String name;

	/**
	 * description
	 */
	@Column(name = "description", columnDefinition = "varchar(2000) not null comment 'subcategory description'")
	private String description;

	/**
	 * tax for this subcategory
	 */
	@Column(name = "tax", columnDefinition = "DECIMAL(7,2) not null comment 'tax percent, 5.5 means 5.5%'")
	private BigDecimal tax;

	/**
	 * Created time
	 */
	@CreatedDate
	@Column(name = "created_date", updatable = false)
	private Date created;

	/**
	 * Updated time
	 */
	@LastModifiedDate
	@Column(name = "updated_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updated;

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(name = "category_id")
	@JsonIgnore
	private CategoryEntity category;
}