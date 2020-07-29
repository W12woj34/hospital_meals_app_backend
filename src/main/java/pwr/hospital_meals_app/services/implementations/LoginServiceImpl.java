package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.LoginDto;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.services.definitions.BaseCrudService;
import pwr.hospital_meals_app.services.definitions.LoginService;
import pwr.hospital_meals_app.services.mappers.LoginMapper;

@Service
public class LoginServiceImpl
        extends BaseCrudService<LoginDto, LoginEntity, Integer, LoginRepository>
        implements LoginService {

    public LoginServiceImpl(LoginRepository repository, LoginMapper mapper) {
        super(repository, mapper);
    }
}
