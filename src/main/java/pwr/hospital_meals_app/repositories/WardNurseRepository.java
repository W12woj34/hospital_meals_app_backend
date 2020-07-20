package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.WardNurseEntity;

@Repository
public interface WardNurseRepository extends BaseRepository<WardNurseEntity, Integer> {

}
