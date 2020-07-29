package pwr.hospital_meals_app.services.definitions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.services.mappers.BaseMapper;

import java.io.Serializable;
import java.util.List;

/**
 * Base class for service supporting CRUD with database, sarching entities using spring specifications
 * and mapping between dto and entities
 *
 * @param <T>  DTO type
 * @param <E>  Entity type
 * @param <ID> Entity ID type
 */
@Service
@Slf4j
public abstract class BaseSpecificationCrudService<
        T extends PersistableDto<ID>,
        E extends Persistable<ID>,
        ID extends Serializable,
        R extends JpaRepository<E, ID> & JpaSpecificationExecutor<E>>
        extends BaseCrudService<T, E, ID, R> implements SpecificationCrudService<T, E, ID> {

    public BaseSpecificationCrudService(R repository, BaseMapper<T, E> mapper) {
        super(repository, mapper);
    }

    @Override
    public List<T> findAll(Specification<E> specification) {
        return mapper.mapToDtoList(repository.findAll(specification));
    }

    @Override
    public Page<T> findAll(Specification<E> specification, Pageable pageable) {
        return repository.findAll(specification, pageable).map(mapper::mapToDto);
    }

    @Override
    public List<T> findAll(Specification<E> specification, Sort sort) {
        return mapper.mapToDtoList(repository.findAll(specification, sort));
    }
}
