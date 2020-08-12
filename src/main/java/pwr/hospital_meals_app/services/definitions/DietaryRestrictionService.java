package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.DietaryRestrictionDto;
import pwr.hospital_meals_app.persistance.entities.DietaryRestrictionEntity;

public interface DietaryRestrictionService extends LoggingCrudService<DietaryRestrictionDto, DietaryRestrictionEntity, Integer> {
}