package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.MealTypeDto;
import pwr.hospital_meals_app.persistance.entities.MealTypeEntity;
import pwr.hospital_meals_app.persistance.repositories.MealTypeRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.MealTypeService;
import pwr.hospital_meals_app.services.mappers.MealTypeMapper;

@Service
public class MealTypeServiceImpl
        extends BaseSpecificationCrudService<MealTypeDto, MealTypeEntity, Integer, MealTypeRepository>
        implements MealTypeService {

    public MealTypeServiceImpl(MealTypeRepository repository, MealTypeMapper mapper) {
        super(repository, mapper);
    }
}
