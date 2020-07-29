package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.services.definitions.MealService;

@RestController
@RequestMapping(RestMappings.MEAL)
public class MealController
        extends BaseRestCrudController<
        MealDto, Integer> {

    public MealController(MealService service) {
        super(service);
    }
}