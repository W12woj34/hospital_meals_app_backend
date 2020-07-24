package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.StayDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.persistance.entities.WardEntity;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.persistance.repositories.PatientRepository;
import pwr.hospital_meals_app.persistance.repositories.WardRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(uses = {EntityFactory.class})
public abstract class StayMapper
        implements BaseMapper<StayDto, StayEntity> {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private WardRepository wardRepository;

    @Override
    @Mapping(target = "patientId", source = "patient.id")
    public abstract StayDto mapToDto(StayEntity entity);

    @Override
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "ward", source = "ward.id")
    public abstract StayEntity mapToEntity(StayDto dto);

    protected PatientEntity patientEntityFromId(Integer id) {
        return patientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    protected WardEntity wardEntityFromId(Integer id) {
        return wardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
