package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.WardDto;
import pwr.hospital_meals_app.persistance.entities.WardEntity;

public interface WardService extends SpecificationCrudService<WardDto, WardEntity, Integer> {
}