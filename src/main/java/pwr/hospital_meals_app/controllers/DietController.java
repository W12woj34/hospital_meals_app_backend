package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.DietDto;
import pwr.hospital_meals_app.services.definitions.DietService;

@RestController
@RequestMapping(RestMappings.DIET)
@Api(tags = "Diets")
public class DietController
        extends BaseRestCrudController<
        DietDto, Integer> {

    public DietController(DietService service) {
        super(service);
    }
}
