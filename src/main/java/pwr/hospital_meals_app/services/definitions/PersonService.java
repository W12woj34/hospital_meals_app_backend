package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.PersonDto;
import pwr.hospital_meals_app.persistance.entities.PersonEntity;

public interface PersonService extends SpecificationCrudService<PersonDto, PersonEntity, Integer> {
}