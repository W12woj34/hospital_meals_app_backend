package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.LogDto;
import pwr.hospital_meals_app.persistance.entities.LogEntity;
import pwr.hospital_meals_app.services.definitions.LogService;
import pwr.hospital_meals_app.specifications.LogSpecification;

@RestController
@RequestMapping(RestMappings.LOG)
@Api(tags = "Logs")
public class LogController
        extends BaseSpecificationCrudController<
        LogDto, Integer, LogEntity, LogSpecification> {

    public LogController(LogService service) {
        super(service);
    }
}
