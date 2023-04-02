package lorekeeper.com.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import lorekeeper.com.domain.Token;
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

    public Token createTokenFromRefresh(String dsOldRefreshToken, String dsIssuer) {

        DecodedJWT decodedJWT = jwtService.decodeToken(dsOldRefreshToken);
        UserSecurity userSecurity = (UserSecurity) userDetailsService.loadUserByUsername(decodedJWT.getSubject());

        String dsAccessToken = jwtService.createAcessToken(userSecurity, dsIssuer, 10);
        String dsNewRefreshToken = jwtService.createRefreshToken(userSecurity, dsIssuer, 60);

        return new Token(dsAccessToken, dsNewRefreshToken);

    }

}
