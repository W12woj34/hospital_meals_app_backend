package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.MealEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends BaseRepository<MealEntity, Integer> {


    List<MealEntity> findByDate(LocalDate date);
}
