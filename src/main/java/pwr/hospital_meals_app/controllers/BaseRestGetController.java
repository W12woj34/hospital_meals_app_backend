package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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


    @ApiOperation(value = "Base rest get all")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Page"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping
    public Page<T> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @ApiOperation(value = "Base rest get by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.ID)
    public T getById(@PathVariable ID id) {
        return service
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_EXCEPTION_MSG + id));
    }

    @ApiOperation(value = "Base check if exist by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.EXIST + RestMappings.ID)
    public boolean existById(@PathVariable ID id) {
        return service.existsById(id);
    }
}
