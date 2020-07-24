package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import pwr.hospital_meals_app.dto.base.DietDto;
import pwr.hospital_meals_app.persistance.entities.DietEntity;

@Mapper
public interface DietMapper extends BaseMapper<DietDto, DietEntity> {
}