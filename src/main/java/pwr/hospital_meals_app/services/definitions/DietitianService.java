package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.DietitianDto;
import pwr.hospital_meals_app.persistance.entities.DietitianEntity;

public interface DietitianService extends SpecificationCrudService<DietitianDto, DietitianEntity, Integer> {
}