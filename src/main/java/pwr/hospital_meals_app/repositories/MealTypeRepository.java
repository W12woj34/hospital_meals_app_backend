package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.MealTypeEntity;

@Repository
public interface MealTypeRepository extends BaseRepository<MealTypeEntity, Integer> {

}
