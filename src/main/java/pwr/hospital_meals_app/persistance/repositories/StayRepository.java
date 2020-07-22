package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.StayEntity;

@Repository
public interface StayRepository extends BaseRepository<StayEntity, Integer> {

}
