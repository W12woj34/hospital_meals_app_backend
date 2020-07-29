package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.PatientMovementDto;
import pwr.hospital_meals_app.persistance.entities.PatientMovementEntity;
import pwr.hospital_meals_app.persistance.repositories.PatientMovementRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.PatientMovementService;
import pwr.hospital_meals_app.services.mappers.PatientMovementMapper;

@Service
public class PatientMovementServiceImpl
        extends BaseSpecificationCrudService<PatientMovementDto, PatientMovementEntity, Integer, PatientMovementRepository>
        implements PatientMovementService {

    public PatientMovementServiceImpl(PatientMovementRepository repository, PatientMovementMapper mapper) {
        super(repository, mapper);
    }
}
