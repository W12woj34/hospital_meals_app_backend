package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.WardNurseDto;
import pwr.hospital_meals_app.services.definitions.WardNurseService;

@RestController
@RequestMapping(RestMappings.WARD_NURSE)
public class WardNurseController
        extends BaseRestCrudController<
        WardNurseDto, Integer> {

    public WardNurseController(WardNurseService service) {
        super(service);
    }
}