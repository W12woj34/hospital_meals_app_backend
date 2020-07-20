package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.PersonEntity;

@Repository
public interface PersonRepository extends BaseRepository<PersonEntity, Integer> {

}
