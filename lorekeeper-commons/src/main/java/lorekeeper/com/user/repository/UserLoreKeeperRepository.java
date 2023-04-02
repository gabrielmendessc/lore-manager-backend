package lorekeeper.com.user.repository;

import lorekeeper.com.user.domain.UserLoreKeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoreKeeperRepository extends JpaRepository<UserLoreKeeper, Integer> {

    Optional<UserLoreKeeper> findByDsEmailOrDsUsername(String dsEmail, String dsUsername);

}
