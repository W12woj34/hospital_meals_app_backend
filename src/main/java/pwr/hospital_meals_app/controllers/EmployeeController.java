package pwr.hospital_meals_app.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pwr.hospital_meals_app.dto.additionals.EmployeeDataDto;
import pwr.hospital_meals_app.dto.base.EmployeeDto;
import pwr.hospital_meals_app.services.definitions.EmployeeService;

@RestController
@RequestMapping(RestMappings.EMPLOYEE)
public class EmployeeController
        extends BaseRestCrudAndPersonalDataController<
        EmployeeDto> {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        super(service);
        this.service = service;
    }

    @GetMapping(RestMappings.DATA)
    public Page<EmployeeDataDto> getEmployeesData(Pageable pageable) {
        return service.getEmployeesData(pageable);
    }

    @GetMapping(RestMappings.DATA + RestMappings.ID)
    public EmployeeDataDto getEmployeeData(@PathVariable Integer id) {
        return service.getEmployeeData(id);
    }

    @GetMapping(RestMappings.DATA + RestMappings.PERSONAL)
    public EmployeeDataDto getEmployeeData(@RequestHeader("Authorization") String token) {
        return service.getEmployeeDataPersonal(token);
    }

}
