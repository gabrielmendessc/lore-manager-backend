package lorekeeper.com.security.utils;

import lorekeeper.com.domain.UserLore;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    public static User fromUserLore(UserLore userLore) {

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
        userLore.getRoleList().forEach(role -> {
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(role.name())
            );
        });

        return new User(userLore.getDsUsername(), userLore.getDsPassword(), simpleGrantedAuthorityList);

    }

}
