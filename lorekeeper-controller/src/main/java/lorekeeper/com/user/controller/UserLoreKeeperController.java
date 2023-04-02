package lorekeeper.com.user.controller;

import lorekeeper.com.crud.controller.AbstractCrudController;
import lorekeeper.com.user.domain.UserLoreKeeper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserLoreKeeperController extends AbstractCrudController<UserLoreKeeper, Integer> {


}
