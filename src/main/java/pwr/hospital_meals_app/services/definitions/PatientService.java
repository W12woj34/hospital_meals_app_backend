package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;

public interface PatientService extends SpecificationCrudService<PatientDto, PatientEntity, Integer> {
}