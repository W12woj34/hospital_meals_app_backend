package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.persistance.entities.MealEntity;
import pwr.hospital_meals_app.persistance.repositories.MealRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.MealService;
import pwr.hospital_meals_app.services.mappers.MealMapper;

@Service
public class MealServiceImpl
        extends BaseSpecificationCrudService<MealDto, MealEntity, Integer, MealRepository>
        implements MealService {

    public MealServiceImpl(MealRepository repository, MealMapper mapper) {
        super(repository, mapper);
    }
}
