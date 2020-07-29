package pwr.hospital_meals_app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.dto.groups.OnCreate;
import pwr.hospital_meals_app.dto.groups.OnPut;
import pwr.hospital_meals_app.services.definitions.CrudService;


import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;

/**
 * Base class for controller supporting REST pattern
 *
 * @param <T>  Type return and get by controller methods
 * @param <ID> Entity ID type
 */
@RestController
@Validated
public abstract class BaseRestCrudController<T extends PersistableDto<ID>, ID extends Serializable>
        extends BaseRestGetController<T, ID> {

    protected static final String ENTITY_EXISTS_EXCEPTION_MSG = "Entity already exists with id=";
    protected static final String ENTITY_NOT_FOUND_EXCEPTION_MSG = "Entity not found with id=";

    private final CrudService<T, ID> service;

    public BaseRestCrudController(CrudService<T, ID> service) {
        super(service);
        this.service = service;
    }

    @Validated(OnCreate.class)
    @PostMapping
    public ResponseEntity<T> create(@Valid @RequestBody T dto, HttpServletRequest request) {
        if (dto.getId() != null && service.existsById(dto.getId())) {
            throw new EntityExistsException(ENTITY_EXISTS_EXCEPTION_MSG + dto.getId());
        }

        T savedEntity = service.save(dto);
        final URI entityMapping =
                new UriTemplate(request.getRequestURI() + RestMappings.ID).expand(savedEntity.getId());

        return ResponseEntity.created(entityMapping).body(savedEntity);
    }

    @DeleteMapping(RestMappings.ID)
    public void deleteById(@PathVariable ID id) {
        if (!service.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MSG + id);
        }

        service.deleteById(id);
    }

    @Validated(OnPut.class)
    @PutMapping(RestMappings.ID)
    public ResponseEntity<T> updateById(@Valid @RequestBody T dto, @PathVariable ID id) {
        T responseDto;

        if (service.existsById(id)) {
            dto.setId(id);
            responseDto = service.save(dto);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_EXCEPTION_MSG + id);
        }

        return ResponseEntity.ok(responseDto);
    }
}
