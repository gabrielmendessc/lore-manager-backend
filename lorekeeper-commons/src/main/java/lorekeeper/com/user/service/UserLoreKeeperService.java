package lorekeeper.com.user.service;

import lorekeeper.com.crud.service.AbstractCrudService;
import lorekeeper.com.user.domain.UserLoreKeeper;
import lorekeeper.com.user.repository.UserLoreKeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoreKeeperService extends AbstractCrudService<UserLoreKeeper, Integer> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserLoreKeeper save(UserLoreKeeper userLoreKeeper) {

        userLoreKeeper.setDsPassword(passwordEncoder.encode(userLoreKeeper.getDsPassword()));

        return super.save(userLoreKeeper);

    }

    @Override
    protected UserLoreKeeper updateData(UserLoreKeeper entityDomain, UserLoreKeeper newDomain) {

        entityDomain = super.updateData(entityDomain, newDomain);
        entityDomain.setDsPassword(passwordEncoder.encode(entityDomain.getDsPassword()));

        return entityDomain;

    }

    public Optional<UserLoreKeeper> findByDsEmailOrDsUsername(String dsEmail, String dsUsername) {

        return getRepository().findByDsEmailOrDsUsername(dsEmail, dsUsername);

    }

    private UserLoreKeeperRepository getRepository() {

        return (UserLoreKeeperRepository) super.repository;

    }

}
