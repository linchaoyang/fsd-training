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
@Table(name = "SELLER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller extends User {

	/**
	 * company name
	 */
	@Column(name = "COMPANY_NAME", unique = true, length = 120)
	@NotNull
	private String companyName;

	/**
	 * contact number
	 */
	@Column(name = "CONTACT_NUMBER", length = 22)
	@NotNull
	private String contactNumber;

	/**
	 * GSTIN
	 */
	@Column(name = "GSTIN", unique = true, length = 60)
	@NotNull
	private String gstin;

	/**
	 * Brief about company
	 */
	@Column(name = "BRIEF", length = 200)
	@NotNull
	private String brief;

	/**
	 * postal address
	 */
	@Column(name = "POSTAL_ADDRESS", length = 200)
	@NotNull
	private String postalAddress;

	/**
	 * Many-To-Many mapping user and role
	 */
	@ManyToMany(cascade = { CascadeType.MERGE, }, fetch = FetchType.EAGER) // cascade = { CascadeType.PERSIST,
																			// CascadeType.MERGE, },
	@JoinTable(name = "seller_srole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	// @JsonIgnore
	private List<SellerRole> roles;
}