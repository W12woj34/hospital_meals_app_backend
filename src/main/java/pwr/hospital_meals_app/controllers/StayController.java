package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.StayDto;
import pwr.hospital_meals_app.services.definitions.StayService;

@RestController
@RequestMapping(RestMappings.STAY)
public class StayController
        extends BaseRestCrudController<
        StayDto, Integer> {

    public StayController(StayService service) {
        super(service);
    }
}