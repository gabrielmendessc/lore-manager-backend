package lorekeeper.com.crud.controller;

import lorekeeper.com.crud.domain.AbstractDomain;
import lorekeeper.com.crud.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public abstract class AbstractCrudController<Domain extends AbstractDomain, Id extends Serializable> implements CrudController<Domain, Id, ResponseEntity<?>> {

    @Autowired
    private AbstractCrudService<Domain, Id> service;

    @Override
    public ResponseEntity<?> save(Domain domain) throws AccessDeniedException {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(domain));

    }

    @Override
    public ResponseEntity<?> update(Domain domain, Id id) throws AccessDeniedException {

        return ResponseEntity.ok(service.update(domain, id));

    }

    @Override
    public ResponseEntity<?> delete(Id id) throws AccessDeniedException {

        service.delete(id);

        return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<?> findById(Id id) throws AccessDeniedException {

        return ResponseEntity.ok(service.findById(id));

    }

    @Override
    public ResponseEntity<?> findAll() throws AccessDeniedException {

        return ResponseEntity.ok(service.findAll());

    }

}
