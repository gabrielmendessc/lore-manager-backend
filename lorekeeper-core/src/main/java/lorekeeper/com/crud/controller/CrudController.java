package lorekeeper.com.crud.controller;

import lorekeeper.com.crud.domain.AbstractDomain;
import org.springframework.security.access.AccessDeniedException;

import java.io.Serializable;

public interface CrudController<Domain extends AbstractDomain, Id extends Serializable, Response> {

    Response save(Domain domain) throws AccessDeniedException;

    Response update(Domain domain, Id id) throws AccessDeniedException;

    Response delete(Id id) throws AccessDeniedException;

    Response findById(Id id) throws AccessDeniedException;

    Response findAll() throws AccessDeniedException;

}
