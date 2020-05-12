package fsd.msservice.transaction.api.domain;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transaction_detail")
@EntityListeners(AuditingEntityListener.class)
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @Column(name = "product_id", columnDefinition = "varchar(64) not null comment 'product id'")
    // private String productId;

    @Column(name = "product_id", columnDefinition = "varchar(64) not null comment 'product id'")
    private String productId;

    @Column(name = "stock", columnDefinition = "int not null comment 'purchase stock number'")
    private Integer stock;

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
    private Transaction detail;

    public ProductCarousel(Integer seq, String imageUrl) {
        this.seq = seq;
        this.imageUrl = imageUrl;
    }
}