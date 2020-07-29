package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.PersonDto;
import pwr.hospital_meals_app.services.definitions.PersonService;

@RestController
@RequestMapping(RestMappings.PERSON)
public class PersonController
        extends BaseRestCrudController<
        PersonDto, Integer> {

    public PersonController(PersonService service) {
        super(service);
    }
}