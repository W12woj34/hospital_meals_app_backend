package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.MealTypeDto;
import pwr.hospital_meals_app.services.definitions.MealTypeService;

@RestController
@RequestMapping(RestMappings.MEAL_TYPE)
@Api(tags = "Meal Types")
public class MealTypeController
        extends BaseRestCrudController<
        MealTypeDto, Integer> {

    public MealTypeController(MealTypeService service) {
        super(service);
    }
}
