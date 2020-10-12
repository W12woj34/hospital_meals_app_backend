package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.DietDto;
import pwr.hospital_meals_app.persistance.entities.DietEntity;
import pwr.hospital_meals_app.persistance.repositories.DietRepository;
import pwr.hospital_meals_app.persistance.repositories.EventRepository;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.services.definitions.*;
import pwr.hospital_meals_app.services.mappers.DietMapper;
import pwr.hospital_meals_app.services.mappers.EventMapper;

@Service
public class DietServiceImpl
        extends BaseLoggingCrudService<DietDto, DietEntity, Integer, DietRepository>
        implements DietService {

    public DietServiceImpl(DietRepository repository,
                            DietMapper mapper,
                            LoginRepository loginRepository,
                            LogService logService,
                            EventMapper eventMapper,
                            EventRepository eventRepository) {

        super(repository, mapper, loginRepository, logService,
                eventRepository, eventMapper, EVENT_TYPE.ORDER_CREATE.getValue(),
                EVENT_TYPE.ORDER_UPDATE.getValue());
    }
}
