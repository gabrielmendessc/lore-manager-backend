package lorekeeper.com.permission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lorekeeper.com.crud.domain.AbstractDomain;
import lorekeeper.com.permission.pk.UserPermissionPK;

@Entity
@Table(name = "user_permission")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserPermission extends AbstractDomain {

    @EmbeddedId
    private UserPermissionPK userPermissionPk;

}
