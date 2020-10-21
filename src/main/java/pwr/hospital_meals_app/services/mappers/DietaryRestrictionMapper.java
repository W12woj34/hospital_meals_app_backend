package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.DietaryRestrictionDto;
import pwr.hospital_meals_app.persistance.entities.DietitianEntity;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.persistance.entities.RestrictionStatusEntity;
import pwr.hospital_meals_app.persistance.entities.DietaryRestrictionEntity;
import pwr.hospital_meals_app.persistance.repositories.DietitianRepository;
import pwr.hospital_meals_app.persistance.repositories.PatientRepository;
import pwr.hospital_meals_app.persistance.repositories.RestrictionStatusRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(uses = {EntityFactory.class})
public abstract class DietaryRestrictionMapper
        implements BaseMapper<DietaryRestrictionDto, DietaryRestrictionEntity> {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RestrictionStatusRepository restrictionStatusRepository;

    @Override
    @Mapping(target = "patientId", source = "patient.id")
    public abstract DietaryRestrictionDto mapToDto(DietaryRestrictionEntity entity);

    @Override
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "status", source = "status.id")
    public abstract DietaryRestrictionEntity mapToEntity(DietaryRestrictionDto dto);

    protected PatientEntity patientEntityFromId(Integer id) {
        return patientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    protected RestrictionStatusEntity RestrictionStatusEntityFromId(Integer id) {
        return restrictionStatusRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
