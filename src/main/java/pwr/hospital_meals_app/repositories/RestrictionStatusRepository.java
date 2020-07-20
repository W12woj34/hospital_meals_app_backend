package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.RestrictionStatusEntity;

@Repository
public interface RestrictionStatusRepository extends BaseRepository<RestrictionStatusEntity, Integer> {

}
