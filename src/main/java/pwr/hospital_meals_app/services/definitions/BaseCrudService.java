package pwr.hospital_meals_app.services.definitions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.services.mappers.BaseMapper;

import java.io.Serializable;
import java.util.Optional;

/**
 * Base class for service supporting CRUD with database using mapping between dto and entities
 *
 * @param <T>  DTO type
 * @param <E>  Entity type
 * @param <ID> Entity ID type
 */
@Service
@Slf4j
public abstract class BaseCrudService<
        T extends PersistableDto<ID>,
        E extends Persistable<ID>,
        ID extends Serializable,
        R extends JpaRepository<E, ID>>
        extends BaseReadService<T, E, ID, R> implements CrudService<T, ID> {

    public BaseCrudService(R repository, BaseMapper<T, E> mapper) {
        super(repository, mapper);
    }

    @Override
    public T save(T dto) {
        E entity = mapper.mapToEntity(dto);
        E savedEntity = repository.save(entity);

        return mapper.mapToDto(savedEntity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<T> updateById(T dto, ID id) {
        Optional<E> entityOptional = repository.findById(id);

        return entityOptional.map(
                entity -> {
                    dto.setId(id);
                    repository.save(mapper.mapToEntity(dto));
                    return mapper.mapToDto(entity);
                });
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
