package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.persistance.entities.PersonEntity;
import pwr.hospital_meals_app.persistance.repositories.PersonRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(uses = {EntityFactory.class})
public abstract class PatientMapper implements BaseMapper<PatientDto, PatientEntity> {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public abstract PatientDto mapToDto(PatientEntity entity);

    @Mapping(target = "person", source = "person.id")
    @Override
    public abstract PatientEntity mapToEntity(PatientDto dto);

    protected PersonEntity personEntityFromId(Integer id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
