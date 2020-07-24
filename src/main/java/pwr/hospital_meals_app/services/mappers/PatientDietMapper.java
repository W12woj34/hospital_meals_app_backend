package pwr.hospital_meals_app.services.mappers;

import javax.persistence.EntityNotFoundException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.PatientDietDto;
import pwr.hospital_meals_app.persistance.entities.DietEntity;
import pwr.hospital_meals_app.persistance.entities.PatientDietEntity;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.persistance.repositories.DietRepository;
import pwr.hospital_meals_app.persistance.repositories.PatientRepository;


@Mapper(uses = {EntityFactory.class})
public abstract class PatientDietMapper implements BaseMapper<PatientDietDto, PatientDietEntity> {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DietRepository dietRepository;

    @Override
    @Mapping(target = "patientId", source = "patient.id")
    public abstract PatientDietDto mapToDto(PatientDietEntity entity);

    @Override
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "diet", source = "diet.id")
    public abstract PatientDietEntity mapToEntity(PatientDietDto dto);

    protected PatientEntity patientEntityFromId(Integer id) {
        return patientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    protected DietEntity dietEntityFromId(Integer id) {
        return dietRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
