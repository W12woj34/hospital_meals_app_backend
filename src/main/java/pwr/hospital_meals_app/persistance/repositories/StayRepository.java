package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.persistance.entities.WardEntity;

import java.util.List;

@Repository
public interface StayRepository extends BaseRepository<StayEntity, Integer> {

    List<StayEntity> findByArchivedAndWard(boolean isArchived, WardEntity ward);

    List<StayEntity> findByWard(WardEntity ward);
}
