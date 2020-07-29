package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.PatientMovementDto;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;
import pwr.hospital_meals_app.persistance.entities.PatientMovementEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(uses = {EntityFactory.class})
public abstract class PatientMovementMapper
        implements BaseMapper<PatientMovementDto, PatientMovementEntity> {

    @Autowired
    private LoginRepository loginRepository;

    @Mapping(source = "login.id", target = "loginId")
    @Override
    public abstract PatientMovementDto mapToDto(PatientMovementEntity entity);

    @Mapping(target = "login", source = "loginId")
    @Override
    public abstract PatientMovementEntity mapToEntity(PatientMovementDto dto);

    protected LoginEntity loginEntityFromId(Integer id) {
        return loginRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
