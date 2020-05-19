package fsd.msservice.product.api.domain;

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

@Data
@NoArgsConstructor
@Entity
@Table(name = "product_carousel")
@EntityListeners(AuditingEntityListener.class)
public class ProductCarousel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// @Column(name = "product_id", columnDefinition = "varchar(64) not null comment
	// 'product id'")
	// private String productId;

	@Column(name = "seq", columnDefinition = "char(1) not null comment 'image carousel index'")
	private Integer seq;

	@Column(name = "image_url", columnDefinition = "varchar(200) not null comment 'image url'")
	private String imageUrl;

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

	@ManyToOne(cascade = { CascadeType.MERGE }) // CascadeType.REFRESH
	@JoinColumn(name = "product_id")
	@JsonIgnore
	private Product product;

	public ProductCarousel(Integer seq, String imageUrl) {
		this.seq = seq;
		this.imageUrl = imageUrl;
	}
}