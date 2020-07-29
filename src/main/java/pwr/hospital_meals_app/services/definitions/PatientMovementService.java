package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.PatientMovementDto;
import pwr.hospital_meals_app.persistance.entities.PatientMovementEntity;

public interface PatientMovementService extends SpecificationCrudService<PatientMovementDto, PatientMovementEntity, Integer> {
}