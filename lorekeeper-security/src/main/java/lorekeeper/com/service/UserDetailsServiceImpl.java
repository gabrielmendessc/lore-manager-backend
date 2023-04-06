package lorekeeper.com.service;

import lorekeeper.com.domain.UserSecurity;
import lorekeeper.com.user.service.UserLoreKeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserLoreKeeperService userLoreKeeperService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        return new UserSecurity(userLoreKeeperService.findByEmailOrUsername(userName, userName).orElseThrow(createException(userName)));

    }

    private Supplier<UsernameNotFoundException> createException(String userName) {

        return () -> new UsernameNotFoundException("User " + userName + " not found!");

    }

}
