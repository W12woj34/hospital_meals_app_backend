package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.OrderStatusDto;
import pwr.hospital_meals_app.persistance.entities.OrderStatusEntity;
import pwr.hospital_meals_app.persistance.repositories.OrderStatusRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.OrderStatusService;
import pwr.hospital_meals_app.services.mappers.OrderStatusMapper;

@Service
public class OrderStatusServiceImpl
        extends BaseSpecificationCrudService<OrderStatusDto, OrderStatusEntity, Integer, OrderStatusRepository>
        implements OrderStatusService {

    public OrderStatusServiceImpl(OrderStatusRepository repository, OrderStatusMapper mapper) {
        super(repository, mapper);
    }
}
