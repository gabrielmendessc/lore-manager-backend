package lorekeeper.com.crud.service;

import jakarta.persistence.Id;
import lombok.SneakyThrows;
import lorekeeper.com.crud.domain.AbstractDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public abstract class AbstractCrudService<Domain extends AbstractDomain, ID extends Serializable> implements CrudService<Domain, ID> {

    @Autowired
    protected JpaRepository<Domain, ID> repository;

    @Override
    public Domain save(Domain domain) {

        return repository.save(domain);

    }

    @Override
    public Domain update(Domain newDomain, ID id) {

        Domain entityDomain = findById(id).orElseThrow();
        entityDomain = updateData(entityDomain, newDomain);

        return repository.save(entityDomain);

    }

    @Override
    public void delete(ID id) {

        repository.deleteById(id);

    }

    @Override
    public Optional<Domain> findById(ID id) {

        return repository.findById(id);

    }

    @Override
    public List<Domain> findAll() {

        return repository.findAll();

    }

    @SneakyThrows
    protected Domain updateData(Domain entityDomain, Domain newDomain) {

        for(Field fieldNew : newDomain.getClass().getDeclaredFields()) {

            fieldNew.setAccessible(true);
            if (fieldNew.isAnnotationPresent(Id.class)) {

                continue;

            }

            Field fieldEntity = entityDomain.getClass().getDeclaredField(fieldNew.getName());
            fieldEntity.setAccessible(true);
            fieldEntity.set(entityDomain, fieldNew.get(newDomain));

        }

        return entityDomain;

    }

}
