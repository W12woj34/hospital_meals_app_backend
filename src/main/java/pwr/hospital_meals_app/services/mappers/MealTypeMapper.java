package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import pwr.hospital_meals_app.dto.base.MealTypeDto;
import pwr.hospital_meals_app.persistance.entities.MealTypeEntity;

@Mapper
public interface MealTypeMapper extends BaseMapper<MealTypeDto, MealTypeEntity> {
}
