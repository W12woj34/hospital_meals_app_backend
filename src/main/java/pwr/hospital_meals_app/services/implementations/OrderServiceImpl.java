package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.OrderDto;
import pwr.hospital_meals_app.persistance.entities.OrderEntity;
import pwr.hospital_meals_app.persistance.repositories.OrderRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.OrderService;
import pwr.hospital_meals_app.services.mappers.OrderMapper;

@Service
public class OrderServiceImpl
        extends BaseSpecificationCrudService<OrderDto, OrderEntity, Integer, OrderRepository>
        implements OrderService {

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        super(repository, mapper);
    }
}
