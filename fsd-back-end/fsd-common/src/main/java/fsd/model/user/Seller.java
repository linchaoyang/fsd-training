package fsd.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
     * serialVersionUID
     */
    private static final long serialVersionUID = -3680988391666425377L;

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

}