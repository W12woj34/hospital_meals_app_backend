package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pwr.hospital_meals_app.dto.base.DietitianDto;
import pwr.hospital_meals_app.persistance.entities.DietitianEntity;

@Mapper(uses = EntityFactory.class)
public abstract class DietitianMapper
        implements BaseMapper<DietitianDto, DietitianEntity> {

    @Mapping(source = "login.id", target = "loginId")
    @Override
    public abstract DietitianDto mapToDto(DietitianEntity entity);

    @Override
    public abstract DietitianEntity mapToEntity(DietitianDto dto);
}