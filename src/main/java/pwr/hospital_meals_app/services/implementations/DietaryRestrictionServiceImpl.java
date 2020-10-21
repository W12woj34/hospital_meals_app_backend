package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.DietaryRestrictionDto;
import pwr.hospital_meals_app.persistance.entities.DietaryRestrictionEntity;
import pwr.hospital_meals_app.persistance.repositories.*;
import pwr.hospital_meals_app.services.definitions.BaseLoggingCrudService;
import pwr.hospital_meals_app.services.definitions.DietaryRestrictionService;
import pwr.hospital_meals_app.services.definitions.EVENT_TYPE;
import pwr.hospital_meals_app.services.definitions.LogService;
import pwr.hospital_meals_app.services.mappers.DietaryRestrictionMapper;
import pwr.hospital_meals_app.services.mappers.EventMapper;

@Service
public class DietaryRestrictionServiceImpl
        extends BaseLoggingCrudService<DietaryRestrictionDto, DietaryRestrictionEntity, Integer, DietaryRestrictionRepository>
        implements DietaryRestrictionService {

    public DietaryRestrictionServiceImpl(DietaryRestrictionRepository repository,
                                         DietaryRestrictionMapper mapper,
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
                EVENT_TYPE.RESTRICTION_CREATE.getValue(),
                EVENT_TYPE.RESTRICTION_UPDATE.getValue());
    }
}
