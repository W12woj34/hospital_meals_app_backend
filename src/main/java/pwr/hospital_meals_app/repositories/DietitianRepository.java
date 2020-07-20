package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.DietitianEntity;

@Repository
public interface DietitianRepository extends BaseRepository<DietitianEntity, Integer> {

}
