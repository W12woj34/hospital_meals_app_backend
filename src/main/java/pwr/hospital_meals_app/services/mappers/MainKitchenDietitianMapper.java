package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pwr.hospital_meals_app.dto.base.MainKitchenDietitianDto;
import pwr.hospital_meals_app.persistance.entities.MainKitchenDietitianEntity;

@Mapper(uses = EntityFactory.class)
public abstract class MainKitchenDietitianMapper
        implements BaseMapper<MainKitchenDietitianDto, MainKitchenDietitianEntity> {

    @Mapping(source = "login.id", target = "loginId")
    @Override
    public abstract MainKitchenDietitianDto mapToDto(MainKitchenDietitianEntity entity);

    @Override
    public abstract MainKitchenDietitianEntity mapToEntity(MainKitchenDietitianDto dto);
}