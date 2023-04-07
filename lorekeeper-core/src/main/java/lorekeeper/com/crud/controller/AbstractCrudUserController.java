package lorekeeper.com.crud.controller;

import lorekeeper.com.crud.domain.AbstractDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public abstract class AbstractCrudUserController<Domain extends AbstractDomain, Id extends Serializable> extends AbstractCrudController<Domain, Id> {

    @Override
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> save(Domain domain) throws AccessDeniedException {
        return super.save(domain);
    }

    @Override
    @PostMapping("/{id}")
    @PreAuthorize("@permissionSecurityService.hasRoleAndUserId('USER', #id)")
    public ResponseEntity<?> update(@RequestBody Domain domain, @PathVariable Id id) throws AccessDeniedException {
        return super.update(domain, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("@permissionSecurityService.hasRoleAndUserId('USER', #id)")
    public ResponseEntity<?> delete(@PathVariable Id id) throws AccessDeniedException {
        return super.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("@permissionSecurityService.hasRoleAndUserId('USER', #id)")
    public ResponseEntity<?> findById(@PathVariable Id id) throws AccessDeniedException {
        return super.findById(id);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAll() throws AccessDeniedException {
        return super.findAll();
    }

}
