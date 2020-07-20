package pwr.hospital_meals_app.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.WardEntity;

@Repository
public interface WardRepository extends BaseRepository<WardEntity, Integer> {

}
