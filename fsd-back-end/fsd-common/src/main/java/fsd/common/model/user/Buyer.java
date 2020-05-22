package fsd.common.model.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import fsd.common.model.user.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BUYER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer extends User {

	/**
	 * name
	 */
	@Column(name = "NAME", length = 64)
	@NotNull
	private String name;

	/**
	 * mobile number
	 */
	@Column(name = "MOBILE", length = 22)
	@NotNull
	private String mobile;

	/**
	 * Many-To-Many mapping user and role
	 */
	@ManyToMany(cascade = { CascadeType.MERGE, }, fetch = FetchType.EAGER) // cascade = { CascadeType.PERSIST,
																			// CascadeType.MERGE, },
	@JoinTable(name = "buyer_brole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	// @JsonIgnore
	private List<BuyerRole> roles;
}