package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.WardDto;
import pwr.hospital_meals_app.services.definitions.WardService;

@RestController
@RequestMapping(RestMappings.WARD)
@Api(tags = "Wards")
public class WardController
        extends BaseRestCrudController<
        WardDto, Integer> {

    public WardController(WardService service) {
        super(service);
    }
}
