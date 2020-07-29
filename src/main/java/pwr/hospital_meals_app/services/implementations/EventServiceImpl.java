package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.EventDto;
import pwr.hospital_meals_app.persistance.entities.EventEntity;
import pwr.hospital_meals_app.persistance.repositories.EventRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.EventService;
import pwr.hospital_meals_app.services.mappers.EventMapper;

@Service
public class EventServiceImpl
        extends BaseSpecificationCrudService<EventDto, EventEntity, Integer, EventRepository>
        implements EventService {

    public EventServiceImpl(EventRepository repository, EventMapper mapper) {
        super(repository, mapper);
    }
}
