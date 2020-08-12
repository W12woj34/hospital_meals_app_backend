package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.services.definitions.PatientService;
import pwr.hospital_meals_app.specifications.PatientSpecification;

@RestController
@RequestMapping(RestMappings.PATIENT)
public class PatientController
        extends BaseSpecificationCrudController<
        PatientDto, Integer, PatientEntity, PatientSpecification> {

    public PatientController(PatientService service) {
        super(service);
    }
}