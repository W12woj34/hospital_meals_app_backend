package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.OrderStatusDto;
import pwr.hospital_meals_app.persistance.entities.OrderStatusEntity;

public interface OrderStatusService extends SpecificationCrudService<OrderStatusDto, OrderStatusEntity, Integer> {
}