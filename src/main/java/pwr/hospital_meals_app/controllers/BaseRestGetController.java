package pwr.hospital_meals_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.services.definitions.ReadService;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;

/**
 * Base class for controller supporting GET requests
 *
 * @param <T>  Type return and get by controller methods
 * @param <ID> Entity ID type
 */
@RequiredArgsConstructor
@RestController
public abstract class BaseRestGetController<T extends PersistableDto<ID>, ID extends Serializable> {

    protected static final String NOT_FOUND_EXCEPTION_MSG = "Entity not found with id=";

    private final ReadService<T, ID> service;

    @GetMapping
    public Page<T> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping(RestMappings.ID)
    public T getById(@PathVariable ID id) {
        return service
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_EXCEPTION_MSG + id));
    }
}
