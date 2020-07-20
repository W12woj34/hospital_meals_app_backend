package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.EventEntity;

@Repository
public interface EventRepository extends BaseRepository<EventEntity, Integer> {

}
