package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.PatientDietDto;
import pwr.hospital_meals_app.services.definitions.PatientDietService;

@RestController
@RequestMapping(RestMappings.PATIENT_DIET)
public class PatientDietController
        extends BaseRestCrudController<
        PatientDietDto, Integer> {

    public PatientDietController(PatientDietService service) {
        super(service);
    }
}