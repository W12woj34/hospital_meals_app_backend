package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.MainKitchenDietitianDto;
import pwr.hospital_meals_app.services.definitions.MainKitchenDietitianService;

@RestController
@RequestMapping(RestMappings.MAIN_KITCHEN_DIETITIAN)
public class MainKitchenDietitianController
        extends BaseRestCrudController<
        MainKitchenDietitianDto, Integer> {

    public MainKitchenDietitianController(MainKitchenDietitianService service) {
        super(service);
    }
}