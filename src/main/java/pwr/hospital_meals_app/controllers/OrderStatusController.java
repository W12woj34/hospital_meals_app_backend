package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.OrderStatusDto;
import pwr.hospital_meals_app.services.definitions.OrderStatusService;

@RestController
@RequestMapping(RestMappings.ORDER_STATUS)
@Api(tags = "Order Statuses")
public class OrderStatusController
        extends BaseRestCrudController<
        OrderStatusDto, Integer> {

    public OrderStatusController(OrderStatusService service) {
        super(service);
    }
}
