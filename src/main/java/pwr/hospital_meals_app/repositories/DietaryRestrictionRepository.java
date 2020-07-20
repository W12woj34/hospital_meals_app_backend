package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.DietaryRestrictionEntity;

@Repository
public interface DietaryRestrictionRepository extends BaseRepository<DietaryRestrictionEntity, Integer> {

}
