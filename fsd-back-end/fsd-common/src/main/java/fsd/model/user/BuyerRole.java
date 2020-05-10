package fsd.model.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import fsd.model.user.base.Role;
import fsd.model.user.base.RoleName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "brole")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BuyerRole extends Role {
  
  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Buyer> users;
  
  public BuyerRole(RoleName name) {
    super(name);
  }

  public BuyerRole() {
    this(RoleName.ROLE_BUYER);
    this.setNote("Buyer role");
  }
}