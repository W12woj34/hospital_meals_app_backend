package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.StayDto;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.persistance.repositories.EventRepository;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.persistance.repositories.OrderRepository;
import pwr.hospital_meals_app.persistance.repositories.StayRepository;
import pwr.hospital_meals_app.services.definitions.BaseLoggingCrudService;
import pwr.hospital_meals_app.services.definitions.EVENT_TYPE;
import pwr.hospital_meals_app.services.definitions.LogService;
import pwr.hospital_meals_app.services.definitions.StayService;
import pwr.hospital_meals_app.services.mappers.EventMapper;
import pwr.hospital_meals_app.services.mappers.OrderMapper;
import pwr.hospital_meals_app.services.mappers.StayMapper;

@Service
public class StayServiceImpl
        extends BaseLoggingCrudService<StayDto, StayEntity, Integer, StayRepository>
        implements StayService {

    public StayServiceImpl(StayRepository repository,
                           StayMapper mapper,
                           LoginRepository loginRepository,
                           LogService logService,
                           EventMapper eventMapper,
                           EventRepository eventRepository) {
        super(repository,
                mapper,
                loginRepository,
                logService,
                eventRepository,
                eventMapper,
                EVENT_TYPE.STAY_CREATE.getValue(),
                EVENT_TYPE.STAY_END.getValue());
    }
}
