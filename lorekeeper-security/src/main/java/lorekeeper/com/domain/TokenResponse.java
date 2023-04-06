package lorekeeper.com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lorekeeper.com.crud.domain.AbstractDomain;

@Getter
@AllArgsConstructor
public class TokenResponse extends AbstractDomain {

    private String accessToken;
    private String refreshToken;

}
