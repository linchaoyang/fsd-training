package fsd.model.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import fsd.model.user.base.Role;
import fsd.model.user.base.RoleName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "srole")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SellerRole extends Role {

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private List<Seller> users;

  public SellerRole(@NotNull RoleName name) {
    super(name);
  }

  public SellerRole() {
    this(RoleName.ROLE_SELLER);
    this.setNote("Seller role");
  }
}