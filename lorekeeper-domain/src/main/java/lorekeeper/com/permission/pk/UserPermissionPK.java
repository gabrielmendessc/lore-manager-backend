package lorekeeper.com.permission.pk;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lorekeeper.com.crud.domain.AbstractDomain;

@Embeddable
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserPermissionPK extends AbstractDomain {

    @JoinColumn(name = "user_lorekeeper", referencedColumnName = "id_user_lorekeeper")
    private Integer idUserLoreKeeper;
    private String permission;
}
