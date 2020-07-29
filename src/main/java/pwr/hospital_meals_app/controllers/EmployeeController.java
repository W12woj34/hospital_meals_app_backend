package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.EmployeeDto;
import pwr.hospital_meals_app.services.definitions.EmployeeService;

@RestController
@RequestMapping(RestMappings.EMPLOYEE)
public class EmployeeController
        extends BaseRestCrudController<
        EmployeeDto, Integer> {

    public EmployeeController(EmployeeService service) {
        super(service);
    }
}