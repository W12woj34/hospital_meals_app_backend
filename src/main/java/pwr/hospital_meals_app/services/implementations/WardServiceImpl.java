package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.WardDto;
import pwr.hospital_meals_app.persistance.entities.WardEntity;
import pwr.hospital_meals_app.persistance.repositories.WardRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.WardService;
import pwr.hospital_meals_app.services.mappers.WardMapper;

@Service
public class WardServiceImpl
        extends BaseSpecificationCrudService<WardDto, WardEntity, Integer, WardRepository>
        implements WardService {

    public WardServiceImpl(WardRepository repository, WardMapper mapper) {
        super(repository, mapper);
    }
}
