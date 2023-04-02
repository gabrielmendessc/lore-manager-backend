package lorekeeper.com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lorekeeper.com.crud.domain.AbstractDomain;

@Getter
@AllArgsConstructor
public class Token extends AbstractDomain {

    private String dsAccessToken;
    private String dsRefreshToken;

}
