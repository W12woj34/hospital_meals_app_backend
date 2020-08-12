package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.OrderDto;
import pwr.hospital_meals_app.persistance.entities.OrderEntity;
import pwr.hospital_meals_app.services.definitions.OrderService;
import pwr.hospital_meals_app.specifications.OrderSpecification;

@RestController
@RequestMapping(RestMappings.ORDER)
public class OrderController
        extends BaseRestCrudWithLoggingController<
        OrderDto, Integer, OrderEntity, OrderSpecification> {

    public OrderController(OrderService service) {
        super(service);
    }
}