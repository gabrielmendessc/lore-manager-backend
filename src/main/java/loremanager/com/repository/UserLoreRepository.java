package loremanager.com.repository;

import loremanager.com.domain.UserLore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoreRepository extends JpaRepository<UserLore, Integer> {

    UserLore findByDsUsername(String dsUsername);

}
