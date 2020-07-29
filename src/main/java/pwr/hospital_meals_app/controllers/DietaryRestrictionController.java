package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.DietaryRestrictionDto;
import pwr.hospital_meals_app.services.definitions.DietaryRestrictionService;

@RestController
@RequestMapping(RestMappings.DIETARY_RESTRICTIONS)
public class DietaryRestrictionController
        extends BaseRestCrudController<
        DietaryRestrictionDto, Integer> {

    public DietaryRestrictionController(DietaryRestrictionService service) {
        super(service);
    }
}