package fsd.msservice.category.api.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Category entity.
 * 
 * @author LinChaoYang
 *
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class) // automaticlly complete created time and updated time
public class CategoryEntity {

	/**
	 * Category id which is generated by database
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * category name
	 */
	@Column(name = "name", columnDefinition = "varchar(200) not null comment 'category name'")
	private String name;

	/**
	 * description
	 */
	@Column(name = "description", columnDefinition = "varchar(2000) not null comment 'category description'")
	private String description;

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
	private Date updated;

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, mappedBy = "category", fetch = FetchType.EAGER)
	private List<SubCategoryEntity> subcategories;

}