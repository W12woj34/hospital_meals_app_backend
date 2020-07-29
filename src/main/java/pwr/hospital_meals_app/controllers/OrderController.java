package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.OrderDto;
import pwr.hospital_meals_app.services.definitions.OrderService;

@RestController
@RequestMapping(RestMappings.ORDER)
public class OrderController
        extends BaseRestCrudController<
        OrderDto, Integer> {

    public OrderController(OrderService service) {
        super(service);
    }
}