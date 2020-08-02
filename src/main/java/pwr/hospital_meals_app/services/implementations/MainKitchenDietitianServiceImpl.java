package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.MainKitchenDietitianDto;
import pwr.hospital_meals_app.persistance.entities.MainKitchenDietitianEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.persistance.repositories.MainKitchenDietitianRepository;
import pwr.hospital_meals_app.services.definitions.BasePersonalCrudService;
import pwr.hospital_meals_app.services.definitions.MainKitchenDietitianService;
import pwr.hospital_meals_app.services.mappers.MainKitchenDietitianMapper;

@Service
public class MainKitchenDietitianServiceImpl
        extends BasePersonalCrudService<MainKitchenDietitianDto, MainKitchenDietitianEntity, MainKitchenDietitianRepository>
        implements MainKitchenDietitianService {

    public MainKitchenDietitianServiceImpl(MainKitchenDietitianRepository repository,
                                           MainKitchenDietitianMapper mapper,
                                           LoginRepository loginRepository) {
        super(repository, mapper, loginRepository);
    }
}
