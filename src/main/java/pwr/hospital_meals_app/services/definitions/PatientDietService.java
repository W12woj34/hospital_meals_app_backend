package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.PatientDietDto;
import pwr.hospital_meals_app.persistance.entities.PatientDietEntity;

public interface PatientDietService extends LoggingCrudService<PatientDietDto, PatientDietEntity, Integer> {
}
