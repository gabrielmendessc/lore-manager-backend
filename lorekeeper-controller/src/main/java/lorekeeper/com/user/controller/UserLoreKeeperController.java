package lorekeeper.com.user.controller;

import lorekeeper.com.crud.controller.AbstractCrudUserController;
import lorekeeper.com.user.domain.UserLoreKeeper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserLoreKeeperController extends AbstractCrudUserController<UserLoreKeeper, Integer> {

    @Override
    public ResponseEntity<?> save(UserLoreKeeper domain) throws AccessDeniedException {

        throw new AccessDeniedException("Access Denied");

    }

}
