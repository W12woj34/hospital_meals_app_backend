package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.PatientDietDto;
import pwr.hospital_meals_app.persistance.entities.PatientDietEntity;
import pwr.hospital_meals_app.persistance.repositories.PatientDietRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.PatientDietService;
import pwr.hospital_meals_app.services.mappers.PatientDietMapper;

@Service
public class PatientDietServiceImpl
        extends BaseSpecificationCrudService<PatientDietDto, PatientDietEntity, Integer, PatientDietRepository>
        implements PatientDietService {

    public PatientDietServiceImpl(PatientDietRepository repository, PatientDietMapper mapper) {
        super(repository, mapper);
    }
}
