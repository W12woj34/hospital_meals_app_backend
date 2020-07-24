package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import pwr.hospital_meals_app.dto.base.RestrictionStatusDto;
import pwr.hospital_meals_app.persistance.entities.RestrictionStatusEntity;

@Mapper
public interface RestrictionStatusMapper extends BaseMapper<RestrictionStatusDto, RestrictionStatusEntity> {
}