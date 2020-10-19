package pwr.hospital_meals_app.services.definitions;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pwr.hospital_meals_app.dto.additionals.PatientDataDto;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;

import java.util.List;

public interface PatientService extends SpecificationCrudService<PatientDto, PatientEntity, Integer> {

    Page<PatientDataDto> getPatientsData(Pageable pageable);

    List<PatientDataDto> getPatientsData();

    Page<PatientDataDto> getPatientsDataFromWard(String token);

    PatientDataDto getPatientData(Integer id);

}
