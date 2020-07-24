package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.persistance.entities.DietEntity;
import pwr.hospital_meals_app.persistance.entities.MealEntity;
import pwr.hospital_meals_app.persistance.entities.MealTypeEntity;
import pwr.hospital_meals_app.persistance.repositories.DietRepository;
import pwr.hospital_meals_app.persistance.repositories.MealTypeRepository;

import javax.persistence.EntityNotFoundException;


@Mapper(uses = {EntityFactory.class})
public abstract class MealMapper implements BaseMapper<MealDto, MealEntity> {

    @Autowired
    private MealTypeRepository mealTypeRepository;
    @Autowired
    private DietRepository dietRepository;

    @Override
    public abstract MealDto mapToDto(MealEntity entity);

    @Override
    @Mapping(target = "type", source = "type.id")
    @Mapping(target = "diet", source = "diet.id")
    public abstract MealEntity mapToEntity(MealDto dto);

    protected MealTypeEntity mealTypeEntityFromId(Integer id) {
        return mealTypeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    protected DietEntity dietEntityFromId(Integer id) {
        return dietRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}