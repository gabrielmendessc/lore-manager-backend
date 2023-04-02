package lorekeeper.com.crud.service;

import lorekeeper.com.crud.domain.AbstractDomain;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudService<Domain extends AbstractDomain, Id extends Serializable> {

    Domain save(Domain domain);

    Domain update(Domain domain, Id id);

    void delete(Id id);

    Optional<Domain> findById(Id id);

    List<Domain> findAll();

}
