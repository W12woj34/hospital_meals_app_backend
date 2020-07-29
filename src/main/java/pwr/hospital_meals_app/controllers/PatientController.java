package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.services.definitions.PatientService;

@RestController
@RequestMapping(RestMappings.PATIENT)
public class PatientController
        extends BaseRestCrudController<
        PatientDto, Integer> {

    public PatientController(PatientService service) {
        super(service);
    }
}