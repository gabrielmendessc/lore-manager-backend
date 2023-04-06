package lorekeeper.com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lorekeeper.com.user.domain.UserLoreKeeper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class UserSecurity implements UserDetails {

    private UserLoreKeeper userLoreKeeper;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton(new SimpleGrantedAuthority(userLoreKeeper.getRole().name()));

    }

    @Override
    public String getPassword() {

        return userLoreKeeper.getPassword();

    }

    @Override
    public String getUsername() {

        return userLoreKeeper.getUsername();

    }

    @Override
    public boolean isAccountNonExpired() {

        return true;

    }

    @Override
    public boolean isAccountNonLocked() {

        return true;

    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;

    }

    @Override
    public boolean isEnabled() {

        return true;

    }

}
