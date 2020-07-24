package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import pwr.hospital_meals_app.dto.base.OrderStatusDto;
import pwr.hospital_meals_app.persistance.entities.OrderStatusEntity;

@Mapper
public interface OrderStatusMapper extends BaseMapper<OrderStatusDto, OrderStatusEntity> {
}