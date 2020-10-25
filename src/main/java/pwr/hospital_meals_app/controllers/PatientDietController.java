package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.PatientDietDto;
import pwr.hospital_meals_app.persistance.entities.PatientDietEntity;
import pwr.hospital_meals_app.services.definitions.PatientDietService;
import pwr.hospital_meals_app.specifications.PatientDietSpecification;

@RestController
@RequestMapping(RestMappings.PATIENT_DIET)
@Api(tags = "Patient Diets")
public class PatientDietController
        extends BaseRestCrudWithLoggingController<
        PatientDietDto, Integer, PatientDietEntity, PatientDietSpecification> {

    public PatientDietController(PatientDietService service) {
        super(service);
    }
}
