package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.PatientMovementEntity;

@Repository
public interface PatientMovementRepository extends BaseRepository<PatientMovementEntity, Integer> {

}
