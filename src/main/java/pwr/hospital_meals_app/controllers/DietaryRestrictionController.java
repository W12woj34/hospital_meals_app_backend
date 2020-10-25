package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.DietaryRestrictionDto;
import pwr.hospital_meals_app.persistance.entities.DietaryRestrictionEntity;
import pwr.hospital_meals_app.services.definitions.DietaryRestrictionService;
import pwr.hospital_meals_app.specifications.DietaryRestrictionSpecification;

@RestController
@RequestMapping(RestMappings.DIETARY_RESTRICTIONS)
@Api(tags = "Dietary Restrictions")
public class DietaryRestrictionController
        extends BaseRestCrudWithLoggingController<
        DietaryRestrictionDto, Integer, DietaryRestrictionEntity, DietaryRestrictionSpecification> {

    public DietaryRestrictionController(DietaryRestrictionService service) {
        super(service);
    }
}
