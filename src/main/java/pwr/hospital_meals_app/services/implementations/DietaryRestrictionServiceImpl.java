package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.DietaryRestrictionDto;
import pwr.hospital_meals_app.persistance.entities.DietaryRestrictionEntity;
import pwr.hospital_meals_app.persistance.repositories.DietaryRestrictionRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.DietaryRestrictionService;
import pwr.hospital_meals_app.services.mappers.DietaryRestrictionMapper;

@Service
public class DietaryRestrictionServiceImpl
        extends BaseSpecificationCrudService<DietaryRestrictionDto, DietaryRestrictionEntity, Integer, DietaryRestrictionRepository>
        implements DietaryRestrictionService {

    public DietaryRestrictionServiceImpl(DietaryRestrictionRepository repository, DietaryRestrictionMapper mapper) {
        super(repository, mapper);
    }
}
