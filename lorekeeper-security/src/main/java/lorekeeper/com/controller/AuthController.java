package lorekeeper.com.controller;

import jakarta.servlet.http.HttpServletRequest;
import lorekeeper.com.domain.TokenResponse;
import lorekeeper.com.service.RefreshService;
import lorekeeper.com.user.domain.UserLoreKeeper;
import lorekeeper.com.user.service.UserLoreKeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserLoreKeeperService userLoreKeeperService;
    @Autowired
    private RefreshService refreshService;

    @PostMapping("/register")
    public ResponseEntity<UserLoreKeeper> saveUser(@RequestBody UserLoreKeeper userLoreKeeper) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userLoreKeeperService.save(userLoreKeeper));

    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest httpServletRequest, @RequestBody TokenResponse tokenResponse) {

        return ResponseEntity.ok(refreshService.createTokenFromRefresh(tokenResponse.getRefreshToken(), httpServletRequest.getRequestURI()));

    }

}