package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.OrderEntity;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, Integer> {

    List<OrderEntity> findByPatient(PatientEntity patient);

}
