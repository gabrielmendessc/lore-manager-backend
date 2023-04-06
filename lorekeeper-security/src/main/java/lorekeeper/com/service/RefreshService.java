package lorekeeper.com.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import lorekeeper.com.domain.TokenResponse;
import lorekeeper.com.domain.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class RefreshService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTService jwtService;

    public TokenResponse createTokenFromRefresh(String oldRefreshToken, String issuer) {

        DecodedJWT decodedJWT = jwtService.decodeToken(oldRefreshToken);
        UserSecurity userSecurity = (UserSecurity) userDetailsService.loadUserByUsername(decodedJWT.getSubject());

        String accessToken = jwtService.createAcessToken(userSecurity, issuer, 10);
        String newRefreshToken = jwtService.createRefreshToken(userSecurity, issuer, 60);

        return new TokenResponse(accessToken, newRefreshToken);

    }

}
