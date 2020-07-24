package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import pwr.hospital_meals_app.dto.base.EventDto;
import pwr.hospital_meals_app.persistance.entities.EventEntity;

@Mapper
public interface EventMapper extends BaseMapper<EventDto, EventEntity> {
}