package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pwr.hospital_meals_app.dto.base.PatientMovementDto;
import pwr.hospital_meals_app.persistance.entities.PatientMovementEntity;

@Mapper(uses = EntityFactory.class)
public abstract class PatientMovementMapper
        implements BaseMapper<PatientMovementDto, PatientMovementEntity> {

    @Mapping(source = "login.id", target = "loginId")
    @Override
    public abstract PatientMovementDto mapToDto(PatientMovementEntity entity);

    @Override
    public abstract PatientMovementEntity mapToEntity(PatientMovementDto dto);
}
