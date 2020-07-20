package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.PatientDietEntity;

@Repository
public interface PatientDietRepository extends BaseRepository<PatientDietEntity, Integer> {

}
