package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.RestrictionStatusDto;
import pwr.hospital_meals_app.persistance.entities.RestrictionStatusEntity;

public interface RestrictionStatusService extends SpecificationCrudService<RestrictionStatusDto, RestrictionStatusEntity, Integer> {
}