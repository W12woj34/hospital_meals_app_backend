package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.LogDto;
import pwr.hospital_meals_app.persistance.entities.LogEntity;

public interface LogService extends SpecificationCrudService<LogDto, LogEntity, Integer> {
}