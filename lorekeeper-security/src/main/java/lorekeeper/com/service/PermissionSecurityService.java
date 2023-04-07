package lorekeeper.com.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PermissionSecurityService {

    private static final String ROLE_PREFIX = "ROLE_";

    public boolean hasRoleAndUserId(String role, Integer idUser) {

        return isAdminFirst(Objects.equals(idUser, getAuthId()) && Objects.equals(ROLE_PREFIX.concat(role), getAuthRole()));

    }

    private boolean isAdminFirst(boolean validation) {

        return "ROLE_ADMIN".equals(getAuthRole()) || validation;

    }

    private String getAuthRole() {

        return getPrincipal().getClaim("role").asString();

    }

    private Integer getAuthId() {

        return getPrincipal().getClaim("idUser").asInt();

    }

    private DecodedJWT getPrincipal() {

        return ((DecodedJWT) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    }

}
