package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.dto.groups.OnCreate;
import pwr.hospital_meals_app.dto.groups.OnPut;
import pwr.hospital_meals_app.services.definitions.LoggingCrudService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;


/**
 * Base class for controller which log specific action in database supporting REST pattern
 *
 * @param <T>  Type return and get by controller methods
 * @param <ID> Entity ID type
 */
@RestController
public abstract class BaseRestCrudWithLoggingController<
        T extends PersistableDto<ID>,
        ID extends Serializable,
        E extends Persistable<ID>,
        S extends Specification<E>>
        extends BaseSpecificationCrudController<T, ID, E, S> {


    private final LoggingCrudService<T, E, ID> service;

    public BaseRestCrudWithLoggingController(LoggingCrudService<T, E, ID> service) {
        super(service);
        this.service = service;
    }


    @ApiOperation(value = "Base rest post with logging")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @Validated(OnCreate.class)
    @PostMapping
    public ResponseEntity<T> create(@RequestHeader("Authorization") String token,
                                    @Valid @RequestBody T dto, HttpServletRequest request) {
        if (dto.getId() != null && service.existsById(dto.getId())) {
            throw new EntityExistsException(ENTITY_EXISTS_EXCEPTION_MSG + dto.getId());
        }

        T savedEntity = service.saveAndLog(dto, token);
        final URI entityMapping =
                new UriTemplate(request.getRequestURI() + RestMappings.ID).expand(savedEntity.getId());

        return ResponseEntity.created(entityMapping).body(savedEntity);
    }

    @ApiOperation(value = "Base rest put by id with logging")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @Validated(OnPut.class)
    @PutMapping(RestMappings.ID)
    public ResponseEntity<T> updateById(@RequestHeader("Authorization") String token,
                                        @Valid @RequestBody T dto,
                                        @PathVariable ID id) {
        T responseDto;

        responseDto = service.updateByIdAndLog(dto, id, token).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_EXCEPTION_MSG + id));

        return ResponseEntity.ok(responseDto);
    }
}
