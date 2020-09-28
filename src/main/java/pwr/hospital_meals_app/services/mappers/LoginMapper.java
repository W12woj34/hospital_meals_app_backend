package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import pwr.hospital_meals_app.dto.base.LoginDto;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;

@Mapper(uses = {EntityFactory.class})
public interface LoginMapper extends BaseMapper<LoginDto, LoginEntity> {
}
