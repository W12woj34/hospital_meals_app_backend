package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.StayDto;
import pwr.hospital_meals_app.persistance.entities.StayEntity;

public interface StayService extends SpecificationCrudService<StayDto, StayEntity, Integer> {
}