package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.EmployeeDto;
import pwr.hospital_meals_app.persistance.entities.EmployeeEntity;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;

import javax.persistence.EntityNotFoundException;


@Mapper(uses = PersonMapper.class)
public abstract class EmployeeMapper implements BaseMapper<EmployeeDto, EmployeeEntity> {

    @Autowired
    private LoginRepository loginRepository;

    @Mapping(source = "login.id", target = "loginId")
    @Override
    public abstract EmployeeDto mapToDto(EmployeeEntity entity);

    @Mapping(source = "loginId", target = "login")
    @Override
    public abstract EmployeeEntity mapToEntity(EmployeeDto dto);

    protected LoginEntity loginEntityFromId(Integer id) {
        return loginRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
