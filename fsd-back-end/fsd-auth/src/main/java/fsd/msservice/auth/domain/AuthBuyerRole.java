package fsd.msservice.auth.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fsd.common.model.user.base.RoleName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "brole")
@Entity
@NoArgsConstructor
public class AuthBuyerRole {

	@Id
	@Column(name = "ID")
	@JsonIgnore
	private Integer id;

	@Column(name = "name")
	@Enumerated(EnumType.STRING)
	private RoleName name;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<AuthBuyer> users;
}