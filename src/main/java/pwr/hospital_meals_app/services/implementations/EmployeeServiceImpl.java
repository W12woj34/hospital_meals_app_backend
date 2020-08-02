package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.EmployeeDto;
import pwr.hospital_meals_app.persistance.entities.EmployeeEntity;
import pwr.hospital_meals_app.persistance.repositories.EmployeeRepository;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.services.definitions.BasePersonalCrudService;
import pwr.hospital_meals_app.services.definitions.EmployeeService;
import pwr.hospital_meals_app.services.mappers.EmployeeMapper;

@Service
public class EmployeeServiceImpl
        extends BasePersonalCrudService<EmployeeDto, EmployeeEntity, EmployeeRepository>
        implements EmployeeService {

    public EmployeeServiceImpl(EmployeeRepository repository,
                               EmployeeMapper mapper,
                               LoginRepository loginRepository) {
        super(repository, mapper, loginRepository);
    }
}
