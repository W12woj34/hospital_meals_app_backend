package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.persistance.entities.WardEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StayRepository extends BaseRepository<StayEntity, Integer> {

    List<StayEntity> findByArchivedAndWard(boolean isArchived, WardEntity ward);

    List<StayEntity> findByWardAndAdmissionDateLessThanEqualAndReleaseDateGreaterThanEqual(WardEntity ward,
                                                                                           LocalDate admissionDate,
                                                                                           LocalDate releaseDate);
}
