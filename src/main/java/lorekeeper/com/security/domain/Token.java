package lorekeeper.com.security.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Token {

    @JsonProperty("acess_token")
    private String dsAcessToken;
    @JsonProperty("refresh_token")
    private String dsRefreshToken;

}
