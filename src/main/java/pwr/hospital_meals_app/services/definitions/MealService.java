package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.persistance.entities.MealEntity;

public interface MealService extends SpecificationCrudService<MealDto, MealEntity, Integer> {
}