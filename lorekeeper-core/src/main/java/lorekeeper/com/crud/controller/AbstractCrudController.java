package lorekeeper.com.crud.controller;

import lorekeeper.com.crud.domain.AbstractDomain;
import lorekeeper.com.crud.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public abstract class AbstractCrudController<Domain extends AbstractDomain, Id extends Serializable> implements CrudController<Domain, Id, ResponseEntity<?>> {

    @Autowired
    private AbstractCrudService<Domain, Id> service;

    @Override
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Domain domain) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(domain));

    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Domain domain, @PathVariable("id") Id id) {

        return ResponseEntity.ok(service.update(domain, id));

    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Id id) {

        service.delete(id);

        return ResponseEntity.noContent().build();

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Id id) {

        return ResponseEntity.ok(service.findById(id));

    }

    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(service.findAll());

    }

}
