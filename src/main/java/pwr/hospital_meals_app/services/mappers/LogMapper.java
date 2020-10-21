package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.LogDto;
import pwr.hospital_meals_app.persistance.entities.EventEntity;
import pwr.hospital_meals_app.persistance.entities.LogEntity;
import pwr.hospital_meals_app.persistance.entities.PersonEntity;
import pwr.hospital_meals_app.persistance.repositories.EventRepository;
import pwr.hospital_meals_app.persistance.repositories.PersonRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(uses = {EntityFactory.class})
public abstract class LogMapper
        implements BaseMapper<LogDto, LogEntity> {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EventRepository eventRepository;

    @Override
    @Mapping(target = "userId", source = "user.id")
    public abstract LogDto mapToDto(LogEntity entity);

    @Override
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "event", source = "event.id")
    public abstract LogEntity mapToEntity(LogDto dto);

    protected PersonEntity personEntityFromId(Integer id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    protected EventEntity eventEntityFromId(Integer id) {
        return eventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
