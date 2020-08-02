package pwr.hospital_meals_app.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.services.definitions.PersonalDataService;

import javax.persistence.EntityNotFoundException;


/**
 * Base class for controller to get personal data from info available in JWT
 *
 * @param <T> Type return and get by controller methods
 */
@RestController
@Validated
public abstract class BaseRestCrudAndPersonalDataController<T extends PersistableDto<Integer>>
        extends BaseRestCrudController<T, Integer> {

    protected static final String ENTITY_NOT_FOUND_EXCEPTION_MSG_TOKEN = "Entity not found from given token";

    private final PersonalDataService<T> service;

    public BaseRestCrudAndPersonalDataController(PersonalDataService<T> service) {
        super(service);
        this.service = service;
    }

    @GetMapping(RestMappings.PERSONAL)
    public T getPersonal(@RequestHeader("Authorization") String token) {
        return service.getPersonal(token).orElseThrow(() ->
                new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MSG_TOKEN));
    }
}
