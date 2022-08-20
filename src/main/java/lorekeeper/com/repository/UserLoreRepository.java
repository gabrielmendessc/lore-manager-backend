package lorekeeper.com.repository;

import lorekeeper.com.domain.UserLore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoreRepository extends JpaRepository<UserLore, Integer> {

    UserLore findByDsUsername(String dsUsername);

    UserLore findByDsUsernameOrDsEmail(String dsUsername, String dsEmail);

}
