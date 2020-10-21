package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.PersonDto;
import pwr.hospital_meals_app.persistance.entities.PersonEntity;
import pwr.hospital_meals_app.services.definitions.PersonService;
import pwr.hospital_meals_app.specifications.PersonSpecification;

@RestController
@RequestMapping(RestMappings.PERSON)
public class PersonController
        extends BaseSpecificationCrudController<
        PersonDto, Integer, PersonEntity, PersonSpecification> {

    public PersonController(PersonService service) {
        super(service);
    }
}
