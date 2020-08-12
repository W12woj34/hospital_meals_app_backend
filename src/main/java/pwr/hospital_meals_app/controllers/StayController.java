package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.StayDto;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.services.definitions.StayService;
import pwr.hospital_meals_app.specifications.StaySpecification;

@RestController
@RequestMapping(RestMappings.STAY)
public class StayController
        extends BaseRestCrudWithLoggingController<
        StayDto, Integer, StayEntity, StaySpecification> {

    public StayController(StayService service) {
        super(service);
    }
}