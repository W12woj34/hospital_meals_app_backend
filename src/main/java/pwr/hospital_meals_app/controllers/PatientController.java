package pwr.hospital_meals_app.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.additionals.PatientDataDto;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.services.definitions.PatientService;
import pwr.hospital_meals_app.specifications.PatientSpecification;

import java.util.List;

@RestController
@RequestMapping(RestMappings.PATIENT)
public class PatientController
        extends BaseSpecificationCrudController<
        PatientDto, Integer, PatientEntity, PatientSpecification> {

    private final PatientService service;

    public PatientController(PatientService service) {
        super(service);
        this.service = service;
    }


    @GetMapping(RestMappings.DATA)
    public Page<PatientDataDto> getPatientsData(Pageable pageable) {
        return service.getPatientsData(pageable);
    }

    @GetMapping(RestMappings.DATA + RestMappings.ID)
    public PatientDataDto getPatientData(@PathVariable Integer id) {
        return service.getPatientData(id);
    }

    @GetMapping(RestMappings.DATA_WARD + RestMappings.ID)
    public Page<PatientDataDto> getPatientsDataWard(@PathVariable Integer id) {
        return service.getPatientsDataFromWard(id);
    }
}
