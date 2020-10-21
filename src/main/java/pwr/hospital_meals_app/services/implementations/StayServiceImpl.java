package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.StayDto;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.persistance.repositories.*;
import pwr.hospital_meals_app.services.definitions.BaseLoggingCrudService;
import pwr.hospital_meals_app.services.definitions.EVENT_TYPE;
import pwr.hospital_meals_app.services.definitions.LogService;
import pwr.hospital_meals_app.services.definitions.StayService;
import pwr.hospital_meals_app.services.mappers.EventMapper;
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
                           OrderRepository orderRepository,
                           PatientDietRepository patientDietRepository,
                           StayRepository stayRepository,
                           DietaryRestrictionRepository dietaryRestrictionRepository,
                           EventRepository eventRepository) {
        super(repository,
                mapper,
                loginRepository,
                logService,
                eventRepository,
                orderRepository,
                patientDietRepository,
                stayRepository,
                dietaryRestrictionRepository,
                eventMapper,
                EVENT_TYPE.STAY_CREATE.getValue(),
                EVENT_TYPE.STAY_END.getValue());
    }
}
