package fsd.msservice.product.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name="suggest_carousel")
@EntityListeners(AuditingEntityListener.class)
public class SuggestCarousel {
    
    @Column(name = "product_id", columnDefinition = "varchar(64) not null comment 'product id'")
    private String productId;

    @Column(name = "seq", columnDefinition = "char(1) not null comment 'image carousel index'")
    private String seq;

    @Column(name = "image_url", columnDefinition = "varchar(200) not null comment 'image url'")
    private String imageUrl;

    @Column(name = "title", columnDefinition = "varchar(60) not null comment 'image title'")
    private String title;

    @Column(name = "description", columnDefinition = "varchar(200) not null comment 'image description'")
    private String description;

    @Column(name = "start_time", columnDefinition = "datetime not null comment 'show start time'")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start;

    @Column(name = "end_time", columnDefinition = "datetime not null comment 'show end time'")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updated;
}