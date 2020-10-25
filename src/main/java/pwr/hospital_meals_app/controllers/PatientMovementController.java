package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.PatientMovementDto;
import pwr.hospital_meals_app.services.definitions.PatientMovementService;

@RestController
@RequestMapping(RestMappings.PATIENT_MOVEMENT)
@Api(tags = "Patient Movement")
public class PatientMovementController
        extends BaseRestCrudController<
        PatientMovementDto, Integer> {

    public PatientMovementController(PatientMovementService service) {
        super(service);
    }
}
