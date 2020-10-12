package pwr.hospital_meals_app.services.implementations;

import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.base.OrderDto;
import pwr.hospital_meals_app.persistance.entities.OrderEntity;
import pwr.hospital_meals_app.persistance.repositories.EventRepository;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.persistance.repositories.OrderRepository;
import pwr.hospital_meals_app.services.definitions.BaseLoggingCrudService;
import pwr.hospital_meals_app.services.definitions.EVENT_TYPE;
import pwr.hospital_meals_app.services.definitions.LogService;
import pwr.hospital_meals_app.services.definitions.OrderService;
import pwr.hospital_meals_app.services.mappers.EventMapper;
import pwr.hospital_meals_app.services.mappers.OrderMapper;

@Service
public class OrderServiceImpl
        extends BaseLoggingCrudService<OrderDto, OrderEntity, Integer, OrderRepository>
        implements OrderService {


    public OrderServiceImpl(OrderRepository repository,
                            OrderMapper mapper,
                            LoginRepository loginRepository,
                            LogService logService,
                            EventMapper eventMapper,
                            EventRepository eventRepository) {
        super(repository, mapper, loginRepository, logService,
                eventRepository, eventMapper, EVENT_TYPE.ORDER_CREATE.getValue(),
                EVENT_TYPE.ORDER_UPDATE.getValue());
    }
}
