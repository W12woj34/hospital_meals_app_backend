package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.DietitianDto;
import pwr.hospital_meals_app.services.definitions.DietitianService;

@RestController
@RequestMapping(RestMappings.DIETITIAN)
@Api(tags = "Dietitians")
public class DietitianController
        extends BaseRestCrudController<
        DietitianDto, Integer> {

    public DietitianController(DietitianService service) {
        super(service);
    }
}
