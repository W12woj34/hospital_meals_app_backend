package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.WardNurseDto;
import pwr.hospital_meals_app.persistance.entities.WardNurseEntity;

public interface WardNurseService extends SpecificationCrudService<WardNurseDto, WardNurseEntity, Integer> {
}