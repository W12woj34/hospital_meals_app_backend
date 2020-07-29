package pwr.hospital_meals_app.services.definitions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.services.mappers.BaseMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 Base class for service supporting reading from database using mapping between dto and entities
 *
 * @param <T>  DTO type
 * @param <E>  Entity type
 * @param <ID> Entity ID type
 */
@Service
@Slf4j
@RequiredArgsConstructor
public abstract class BaseReadService<
        T extends PersistableDto<ID>,
        E extends Persistable<ID>,
        ID extends Serializable,
        R extends JpaRepository<E, ID>>
        implements ReadService<T, ID> {

    protected final R repository;
    protected final BaseMapper<T, E> mapper;

    @Override
    public Page<T> findAll(Pageable pageable) {
        Page<E> allEntities = repository.findAll(pageable);

        return allEntities.map(mapper::mapToDto);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        Optional<E> entityOptional = repository.findById(id);

        return entityOptional.map(mapper::mapToDto);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
