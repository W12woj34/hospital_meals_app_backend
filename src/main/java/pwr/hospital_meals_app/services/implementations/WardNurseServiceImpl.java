package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.WardNurseDto;
import pwr.hospital_meals_app.persistance.entities.WardNurseEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.persistance.repositories.WardNurseRepository;
import pwr.hospital_meals_app.services.definitions.BasePersonalCrudService;
import pwr.hospital_meals_app.services.definitions.WardNurseService;
import pwr.hospital_meals_app.services.mappers.WardNurseMapper;

@Service
public class WardNurseServiceImpl
        extends BasePersonalCrudService<WardNurseDto, WardNurseEntity, WardNurseRepository>
        implements WardNurseService {

    public WardNurseServiceImpl(WardNurseRepository repository,
                                WardNurseMapper mapper,
                                LoginRepository loginRepository) {
        super(repository, mapper, loginRepository);
    }
}
