package lorekeeper.com.domain;

import lombok.AllArgsConstructor;
import lorekeeper.com.user.domain.UserLoreKeeper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class UserSecurity implements UserDetails {

    private UserLoreKeeper userLoreKeeper;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return userLoreKeeper.getRoleList().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();

    }

    @Override
    public String getPassword() {

        return userLoreKeeper.getDsPassword();

    }

    @Override
    public String getUsername() {

        return userLoreKeeper.getDsUsername();

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
