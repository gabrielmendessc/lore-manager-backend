package loremanager.com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import loremanager.com.security.enums.Role;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity @Table(name = "tb_user")
@Getter @Setter
public class UserLore {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    @JsonProperty("id_user")
    private Integer idUser;
    @Column(name = "username", unique = true, nullable = false)
    @JsonProperty("username")
    private String dsUsername;
    @Column(name = "email", unique = true, nullable = false)
    @JsonProperty("email")
    private String dsEmail;
    @Column(name = "password", nullable = false)
    @JsonProperty("password")
    private String dsPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "tb_role")
    @Column(name = "role", nullable = false)
    @JsonProperty("roles")
    private List<Role> roleList;

}
