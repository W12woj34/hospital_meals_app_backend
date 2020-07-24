package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import pwr.hospital_meals_app.dto.base.WardDto;
import pwr.hospital_meals_app.persistance.entities.WardEntity;

@Mapper
public interface WardMapper extends BaseMapper<WardDto, WardEntity> {
}