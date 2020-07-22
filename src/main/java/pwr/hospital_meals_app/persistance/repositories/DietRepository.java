package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.DietEntity;

@Repository
public interface DietRepository extends BaseRepository<DietEntity, Integer> {

}
