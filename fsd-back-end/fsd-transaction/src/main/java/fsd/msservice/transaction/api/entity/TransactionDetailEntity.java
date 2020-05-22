package fsd.msservice.transaction.api.entity;

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
 * Transaction detail that demonstrates how many products were purchased.
 * 
 * @author LinChaoYang
 *
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "transaction_detail")
@EntityListeners(AuditingEntityListener.class)
public class TransactionDetailEntity {

	/**
	 * Transaction detail ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * sequence
	 */
	@Column(name = "seq", columnDefinition = "char(1) not null comment 'sequence'")
	private String seq;

	/**
	 * Product id contained in this transaction
	 */
	@Column(name = "product_id", columnDefinition = "varchar(64) not null comment 'product id'")
	private String productId;

	/**
	 * Product name
	 */
	@Column(name = "product_name", columnDefinition = "varchar(200) not null comment 'product name snapshot'")
	private String productName;

	/**
	 * Seller id
	 */
	@Column(name = "seller_id", columnDefinition = "varchar(64) not null comment 'seller id'")
	private String sellerId;

	/**
	 * Seller name
	 */
	@Column(name = "seller_name", columnDefinition = "varchar(200) not null comment 'seller name'")
	private String sellerName;

	/**
	 * Purchased stock number for this product
	 */
	@Column(name = "stock_number", columnDefinition = "int not null comment 'purchase stock number'")
	private int stock;

	/**
	 * Image url
	 */
	@Column(name = "image_url", columnDefinition = "varchar(200) not null comment 'image url snapshot'")
	private String imageUrl;

	/**
	 * Purchased price
	 */
	@Column(name = "price", columnDefinition = "DECIMAL(7,2) not null comment 'purchased price'")
	private BigDecimal price;

	/**
	 * Purchased total money amount
	 */
	@Column(name = "total_amount", columnDefinition = "DECIMAL(7,2) not null comment 'purchased money amount'")
	private BigDecimal totalAmount;

	/**
	 * Total tax for this product
	 */
	@Column(name = "total_tax", columnDefinition = "DECIMAL(7,2) not null comment 'purchased tax'")
	private BigDecimal totalTax;

	/**
	 * Created time
	 */
	@CreatedDate
	@Column(name = "created_date", updatable = false)
	@JsonIgnore // Ignore this field when generate to Json
	private Date created;

	/**
	 * Updated time
	 */
	@LastModifiedDate
	@Column(name = "updated_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updated;

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }) //
	@JoinColumn(name = "transaction_id")
	@JsonIgnore
	private TransactionEntity transaction;
}