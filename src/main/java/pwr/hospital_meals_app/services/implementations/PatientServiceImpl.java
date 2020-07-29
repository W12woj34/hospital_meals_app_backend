package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.persistance.repositories.PatientRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.PatientService;
import pwr.hospital_meals_app.services.mappers.PatientMapper;

@Service
public class PatientServiceImpl
        extends BaseSpecificationCrudService<PatientDto, PatientEntity, Integer, PatientRepository>
        implements PatientService {

    public PatientServiceImpl(PatientRepository repository, PatientMapper mapper) {
        super(repository, mapper);
    }
}
