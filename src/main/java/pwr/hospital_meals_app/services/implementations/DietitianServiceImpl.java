package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.DietitianDto;
import pwr.hospital_meals_app.persistance.entities.DietitianEntity;
import pwr.hospital_meals_app.persistance.repositories.DietitianRepository;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.services.definitions.BasePersonalCrudService;
import pwr.hospital_meals_app.services.definitions.DietitianService;
import pwr.hospital_meals_app.services.mappers.DietitianMapper;

@Service
public class DietitianServiceImpl
        extends BasePersonalCrudService<DietitianDto, DietitianEntity, DietitianRepository>
        implements DietitianService {

    public DietitianServiceImpl(DietitianRepository repository,
                                DietitianMapper mapper,
                                LoginRepository loginRepository) {
        super(repository, mapper, loginRepository);
    }
}
