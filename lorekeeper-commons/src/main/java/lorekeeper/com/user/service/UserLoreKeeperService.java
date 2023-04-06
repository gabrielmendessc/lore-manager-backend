package lorekeeper.com.user.service;

import lombok.SneakyThrows;
import lorekeeper.com.crud.service.AbstractCrudService;
import lorekeeper.com.user.domain.UserLoreKeeper;
import lorekeeper.com.user.enums.Role;
import lorekeeper.com.user.repository.UserLoreKeeperRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoreKeeperService extends AbstractCrudService<UserLoreKeeper, Integer> {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @SneakyThrows
    @Override
    public UserLoreKeeper save(UserLoreKeeper userLoreKeeper) {

        if (Role.ROLE_ADMIN.equals(userLoreKeeper.getRole())) {

            throw new Exception("Can't create user with ADMIN role.");

        }

        userLoreKeeper.setPassword(passwordEncoder.encode(userLoreKeeper.getPassword()));

        return super.save(userLoreKeeper);

    }

    @Override
    protected UserLoreKeeper updateData(UserLoreKeeper entityDomain, UserLoreKeeper newDomain) {

        entityDomain = super.updateData(entityDomain, newDomain);
        entityDomain.setPassword(passwordEncoder.encode(entityDomain.getPassword()));

        return entityDomain;

    }

    public Optional<UserLoreKeeper> findByEmailOrUsername(String email, String username) {

        return getRepository().findByEmailOrUsername(email, username);

    }

    private UserLoreKeeperRepository getRepository() {

        return (UserLoreKeeperRepository) super.repository;

    }

}
