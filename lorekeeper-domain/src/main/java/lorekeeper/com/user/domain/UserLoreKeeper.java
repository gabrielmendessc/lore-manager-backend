package lorekeeper.com.user.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lorekeeper.com.crud.domain.AbstractDomain;
import lorekeeper.com.user.enums.Role;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class UserLoreKeeper extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUserLoreKeeper;
    @Column(name = "username", unique = true, nullable = false)
    private String dsUsername;
    @Column(name = "email", unique = true, nullable = false)
    private String dsEmail;
    @Column(name = "password", nullable = false)
    private String dsPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_role")
    @Column(name = "role", nullable = false)
    private List<Role> roleList;

}
