package lorekeeper.com.crud.controller;

import lorekeeper.com.crud.domain.AbstractDomain;

import java.io.Serializable;

public interface CrudController<Domain extends AbstractDomain, Id extends Serializable, Response> {

    Response save(Domain domain);

    Response update(Domain domain, Id id);

    Response delete(Id id);

    Response findById(Id id);

    Response findAll();

}
