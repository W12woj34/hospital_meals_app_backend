package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.StayDto;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.persistance.repositories.StayRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.StayService;
import pwr.hospital_meals_app.services.mappers.StayMapper;

@Service
public class StayServiceImpl
        extends BaseSpecificationCrudService<StayDto, StayEntity, Integer, StayRepository>
        implements StayService {

    public StayServiceImpl(StayRepository repository, StayMapper mapper) {
        super(repository, mapper);
    }
}
