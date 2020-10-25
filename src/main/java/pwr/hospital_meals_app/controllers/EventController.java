package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.EventDto;
import pwr.hospital_meals_app.services.definitions.EventService;

@RestController
@RequestMapping(RestMappings.EVENT)
@Api(tags = "Events")
public class EventController
        extends BaseRestCrudController<
        EventDto, Integer> {

    public EventController(EventService service) {
        super(service);
    }
}
