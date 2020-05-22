package fsd.common.model.user.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar(50) not null comment 'role name'")
	@Enumerated(EnumType.STRING)
	private RoleName name;

	@Column(name = "note", columnDefinition = "varchar(200) not null comment 'role note'")
	private String note;

	@CreatedDate
	@Column(name = "created_date", updatable = false)
	@JsonIgnore
	private Date created;

	@LastModifiedDate
	@Column(name = "updated_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updated;

	public Role(@NotNull RoleName name) {
		super();
		this.name = name;
	}
}