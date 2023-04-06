package lorekeeper.com.domain;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public JWTAuthenticationToken(DecodedJWT principal, Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        super.setAuthenticated(true);

        this.principal = principal;

    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
