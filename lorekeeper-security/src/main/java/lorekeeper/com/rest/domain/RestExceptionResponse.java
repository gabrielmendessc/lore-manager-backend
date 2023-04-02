package lorekeeper.com.rest.domain;

import lombok.Builder;
import lombok.Getter;
import lorekeeper.com.crud.domain.AbstractDomain;

import java.time.Instant;

@Builder
@Getter
public class RestExceptionResponse extends AbstractDomain {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
