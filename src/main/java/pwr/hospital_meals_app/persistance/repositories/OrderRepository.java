package pwr.hospital_meals_app.persistance.repositories;

import org.springframework.stereotype.Repository;
import pwr.hospital_meals_app.persistance.entities.OrderEntity;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, Integer> {

}
