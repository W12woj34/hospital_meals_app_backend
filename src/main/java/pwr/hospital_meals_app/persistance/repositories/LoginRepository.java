package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;

@Repository
public interface LoginRepository extends BaseRepository<LoginEntity, Integer> {

}
