package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.LogDto;
import pwr.hospital_meals_app.persistance.entities.LogEntity;
import pwr.hospital_meals_app.persistance.repositories.LogRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.LogService;
import pwr.hospital_meals_app.services.mappers.LogMapper;

@Service
public class LogServiceImpl
        extends BaseSpecificationCrudService<LogDto, LogEntity, Integer, LogRepository>
        implements LogService {

    public LogServiceImpl(LogRepository repository, LogMapper mapper) {
        super(repository, mapper);
    }
}
