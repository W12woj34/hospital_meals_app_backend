package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.DietitianDto;
import pwr.hospital_meals_app.persistance.entities.DietitianEntity;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(uses = {EntityFactory.class})
public abstract class DietitianMapper
        implements BaseMapper<DietitianDto, DietitianEntity> {

    @Autowired
    private LoginRepository loginRepository;

    @Mapping(source = "login.id", target = "loginId")
    @Override
    public abstract DietitianDto mapToDto(DietitianEntity entity);

    @Mapping(target = "login", source = "loginId")
    @Override
    public abstract DietitianEntity mapToEntity(DietitianDto dto);

    protected LoginEntity loginEntityFromId(Integer id) {
        return loginRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}