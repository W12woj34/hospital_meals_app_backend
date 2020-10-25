package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.RestrictionStatusDto;
import pwr.hospital_meals_app.services.definitions.RestrictionStatusService;

@RestController
@RequestMapping(RestMappings.RESTRICTION_STATUS)
@Api(tags = "Restriction Statuses")
public class RestrictionStatusController
        extends BaseRestCrudController<
        RestrictionStatusDto, Integer> {

    public RestrictionStatusController(RestrictionStatusService service) {
        super(service);
    }
}
