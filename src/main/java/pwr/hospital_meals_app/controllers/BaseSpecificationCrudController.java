package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.services.definitions.SpecificationCrudService;

import java.io.Serializable;

@RestController
public abstract class BaseSpecificationCrudController<
        T extends PersistableDto<ID>,
        ID extends Serializable,
        E extends Persistable<ID>,
        S extends Specification<E>>
        extends BaseRestCrudController<T, ID> {
    private final SpecificationCrudService<T, E, ID> service;

    public BaseSpecificationCrudController(SpecificationCrudService<T, E, ID> service) {
        super(service);
        this.service = service;
    }

    @ApiOperation(value = "Base rest get all with given specification")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Page"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.SEARCH)
    public Page<T> getAllWithGivenParameters(S specification, Pageable pageable) {
        return service.findAll(specification, pageable);
    }
}
