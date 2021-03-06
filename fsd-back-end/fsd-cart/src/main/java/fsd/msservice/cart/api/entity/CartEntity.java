package fsd.msservice.cart.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "cart")
@EntityListeners(AuditingEntityListener.class) // automaticlly complete created time and updated time
public class CartEntity {

	/**
	 * cart id which is generated by database
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * product id
	 */
	@Column(name = "product_id", columnDefinition = "varchar(64) not null comment 'product id'")
	private String productId;

	/**
	 * buyer id
	 */
	@Column(name = "buyer_id", columnDefinition = "varchar(64) not null comment 'buyer id'")
	private String buyerId;

	/**
	 * purchase stock number
	 */
	@Column(name = "stock_number", columnDefinition = "int not null comment 'purchase stock number'")
	private Integer stockNumber;

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

}