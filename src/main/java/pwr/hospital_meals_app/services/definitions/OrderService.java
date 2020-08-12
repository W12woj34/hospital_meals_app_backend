package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.OrderDto;
import pwr.hospital_meals_app.persistance.entities.OrderEntity;

public interface OrderService extends LoggingCrudService<OrderDto, OrderEntity, Integer> {
}