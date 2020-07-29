package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.RestrictionStatusDto;
import pwr.hospital_meals_app.persistance.entities.RestrictionStatusEntity;
import pwr.hospital_meals_app.persistance.repositories.RestrictionStatusRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.RestrictionStatusService;
import pwr.hospital_meals_app.services.mappers.RestrictionStatusMapper;

@Service
public class RestrictionStatusServiceImpl
        extends BaseSpecificationCrudService<RestrictionStatusDto, RestrictionStatusEntity, Integer, RestrictionStatusRepository>
        implements RestrictionStatusService {

    public RestrictionStatusServiceImpl(RestrictionStatusRepository repository, RestrictionStatusMapper mapper) {
        super(repository, mapper);
    }
}
