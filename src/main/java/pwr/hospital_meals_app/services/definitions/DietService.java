package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.DietDto;
import pwr.hospital_meals_app.persistance.entities.DietEntity;

public interface DietService extends LoggingCrudService<DietDto, DietEntity, Integer> {
}
