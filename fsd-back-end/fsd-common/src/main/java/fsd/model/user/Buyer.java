package fsd.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
     * serialVersionUID
     */
    private static final long serialVersionUID = -7637514706277525907L;

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
}