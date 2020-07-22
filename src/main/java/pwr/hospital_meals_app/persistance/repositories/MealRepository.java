package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.MealEntity;

@Repository
public interface MealRepository extends BaseRepository<MealEntity, Integer> {

}
