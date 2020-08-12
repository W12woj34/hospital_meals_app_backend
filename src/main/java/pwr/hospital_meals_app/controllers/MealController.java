package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.persistance.entities.MealEntity;
import pwr.hospital_meals_app.services.definitions.MealService;
import pwr.hospital_meals_app.specifications.MealSpecification;

@RestController
@RequestMapping(RestMappings.MEAL)
public class MealController
        extends BaseSpecificationCrudController<
        MealDto, Integer, MealEntity, MealSpecification> {

    public MealController(MealService service) {
        super(service);
    }
}