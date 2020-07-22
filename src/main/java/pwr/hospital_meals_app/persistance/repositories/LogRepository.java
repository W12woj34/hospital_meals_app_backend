package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.LogEntity;

@Repository
public interface LogRepository extends BaseRepository<LogEntity, Integer> {

}
