package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.MealTypeDto;
import pwr.hospital_meals_app.persistance.entities.MealTypeEntity;

public interface MealTypeService extends SpecificationCrudService<MealTypeDto, MealTypeEntity, Integer> {
}