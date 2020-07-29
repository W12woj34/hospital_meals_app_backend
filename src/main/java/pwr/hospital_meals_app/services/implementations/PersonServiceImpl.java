package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.PersonDto;
import pwr.hospital_meals_app.persistance.entities.PersonEntity;
import pwr.hospital_meals_app.persistance.repositories.PersonRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.PersonService;
import pwr.hospital_meals_app.services.mappers.PersonMapper;

@Service
public class PersonServiceImpl
        extends BaseSpecificationCrudService<PersonDto, PersonEntity, Integer, PersonRepository>
        implements PersonService {

    public PersonServiceImpl(PersonRepository repository, PersonMapper mapper) {
        super(repository, mapper);
    }
}
