package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;

@Repository
public interface PatientRepository extends BaseRepository<PatientEntity, Integer> {

}
