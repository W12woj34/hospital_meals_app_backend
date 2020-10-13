package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.PatientDietDto;
import pwr.hospital_meals_app.persistance.entities.PatientDietEntity;
import pwr.hospital_meals_app.persistance.repositories.*;
import pwr.hospital_meals_app.services.definitions.*;
import pwr.hospital_meals_app.services.mappers.EventMapper;
import pwr.hospital_meals_app.services.mappers.PatientDietMapper;

@Service
public class PatientDietServiceImpl
        extends BaseLoggingCrudService<PatientDietDto, PatientDietEntity, Integer, PatientDietRepository>
        implements PatientDietService {

    public PatientDietServiceImpl(PatientDietRepository repository,
                                  PatientDietMapper mapper,
                                  LoginRepository loginRepository,
                                  LogService logService,
                                  EventMapper eventMapper,
                                  EventRepository eventRepository) {
        super(repository, mapper, loginRepository, logService,
                eventRepository, eventMapper, EVENT_TYPE.DIET_UPDATE.getValue(),
                EVENT_TYPE.DIET_UPDATE.getValue());
    }
}
