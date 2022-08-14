package loremanager.com.service;

import lombok.RequiredArgsConstructor;
import loremanager.com.domain.UserLore;
import loremanager.com.repository.UserLoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLoreService implements UserDetailsService {

    @Autowired
    private final UserLoreRepository userLoreRepository;

    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserLore userLore = findByDsUsername(username);
        if (Objects.nonNull(userLore)) {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            userLore.getRoleList().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.name()));
            });

            return new User(userLore.getDsUsername(), userLore.getDsPassword(), authorities);
        }

        throw new UsernameNotFoundException("User "+username+" not found!");

    }

    public List<UserLore> findAll(){

        return userLoreRepository.findAll();

    }

    public UserLore findById(Integer id){

        Optional<UserLore> user = userLoreRepository.findById(id);

        return user.orElse(null);

    }

    public UserLore findByDsUsername(String dsUsername){

        return userLoreRepository.findByDsUsername(dsUsername);

    }

    public UserLore findByDsEmail(String dsEmail) {

        return userLoreRepository.findByDsEmail(dsEmail);

    }

    public UserLore saveUser(UserLore userLore) {

        userLore.setDsPassword(passwordEncoder().encode(userLore.getDsPassword()));

        return userLoreRepository.save(userLore);

    }

    public UserLore updateUser(UserLore userLore) {

        UserLore userLoreEntity = userLoreRepository.getById(userLore.getIdUser());

        updateData(userLoreEntity, userLore);

        return userLoreRepository.save(userLoreEntity);

    }

    public void deleteUser(UserLore userLore) {

        userLoreRepository.deleteById(userLore.getIdUser());

    }

    private void updateData(UserLore userLoreEntity, UserLore userLore){

        userLoreEntity.setDsEmail(userLore.getDsEmail());
        userLoreEntity.setDsPassword(userLore.getDsPassword());
        userLoreEntity.setDsUsername(userLore.getDsUsername());
        userLoreEntity.setRoleList(userLore.getRoleList());

    }

}
