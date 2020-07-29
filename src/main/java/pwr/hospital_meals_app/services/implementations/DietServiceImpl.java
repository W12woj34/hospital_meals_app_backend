package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.DietDto;
import pwr.hospital_meals_app.persistance.entities.DietEntity;
import pwr.hospital_meals_app.persistance.repositories.DietRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.DietService;
import pwr.hospital_meals_app.services.mappers.DietMapper;

@Service
public class DietServiceImpl
        extends BaseSpecificationCrudService<DietDto, DietEntity, Integer, DietRepository>
        implements DietService {

    public DietServiceImpl(DietRepository repository, DietMapper mapper) {
        super(repository, mapper);
    }
}
