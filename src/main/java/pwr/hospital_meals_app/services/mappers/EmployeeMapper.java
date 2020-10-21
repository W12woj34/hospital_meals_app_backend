package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.EmployeeDto;
import pwr.hospital_meals_app.persistance.entities.EmployeeEntity;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;
import pwr.hospital_meals_app.persistance.entities.PersonEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.persistance.repositories.PersonRepository;

import javax.persistence.EntityNotFoundException;


@Mapper(uses = {EntityFactory.class})
public abstract class EmployeeMapper implements BaseMapper<EmployeeDto, EmployeeEntity> {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PersonRepository personRepository;

    @Mapping(source = "login.id", target = "loginId")
    @Override
    public abstract EmployeeDto mapToDto(EmployeeEntity entity);

    @Mapping(source = "loginId", target = "login")
    @Mapping(target = "person", source = "person.id")
    @Override
    public abstract EmployeeEntity mapToEntity(EmployeeDto dto);

    protected LoginEntity loginEntityFromId(Integer id) {
        return loginRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    protected PersonEntity personEntityFromId(Integer id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
