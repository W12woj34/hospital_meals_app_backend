package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.WardNurseDto;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;
import pwr.hospital_meals_app.persistance.entities.WardEntity;
import pwr.hospital_meals_app.persistance.entities.WardNurseEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.persistance.repositories.WardRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(uses = {EntityFactory.class, WardMapper.class})
public abstract class WardNurseMapper
        implements BaseMapper<WardNurseDto, WardNurseEntity> {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private WardRepository wardRepository;

    @Mapping(source = "login.id", target = "loginId")
    @Override
    public abstract WardNurseDto mapToDto(WardNurseEntity entity);

    @Mapping(target = "login", source = "loginId")
    @Mapping(target = "ward", source = "ward.id")
    @Override
    public abstract WardNurseEntity mapToEntity(WardNurseDto dto);

    protected LoginEntity loginEntityFromId(Integer id) {
        return loginRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    protected WardEntity skillEntityFromId(Integer id) {
        return wardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}