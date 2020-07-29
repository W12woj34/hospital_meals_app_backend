package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.LogDto;
import pwr.hospital_meals_app.services.definitions.LogService;

@RestController
@RequestMapping(RestMappings.LOG)
public class LogController
        extends BaseRestCrudController<
        LogDto, Integer> {

    public LogController(LogService service) {
        super(service);
    }
}