package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import pwr.hospital_meals_app.dto.base.PersonDto;
import pwr.hospital_meals_app.persistance.entities.PersonEntity;

@Mapper(uses = EntityFactory.class)
public interface PersonMapper extends BaseMapper<PersonDto, PersonEntity> {}
