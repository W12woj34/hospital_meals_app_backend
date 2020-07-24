package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;

@Mapper(uses = {PersonMapper.class, EntityFactory.class})
public interface PatientMapper extends BaseMapper<PatientDto, PatientEntity> {}
