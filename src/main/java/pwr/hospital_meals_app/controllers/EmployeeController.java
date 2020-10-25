package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pwr.hospital_meals_app.dto.additionals.EmployeeDataDto;
import pwr.hospital_meals_app.dto.base.EmployeeDto;
import pwr.hospital_meals_app.services.definitions.EmployeeService;

@RestController
@RequestMapping(RestMappings.EMPLOYEE)
@Api(tags = "Employees")
public class EmployeeController
        extends BaseRestCrudAndPersonalDataController<
        EmployeeDto> {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        super(service);
        this.service = service;
    }

    @ApiOperation(value = "Get relevant employees data collected")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Page"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.DATA)
    public Page<EmployeeDataDto> getEmployeesData(Pageable pageable) {
        return service.getEmployeesData(pageable);
    }


    @ApiOperation(value = "Get relevant employee data collected by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.DATA + RestMappings.ID)
    public EmployeeDataDto getEmployeeData(@PathVariable Integer id) {
        return service.getEmployeeData(id);
    }


    @ApiOperation(value = "Get relevant employee data collected by id from token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.DATA + RestMappings.PERSONAL)
    public EmployeeDataDto getEmployeeData(@RequestHeader("Authorization") String token) {
        return service.getEmployeeDataPersonal(token);
    }

}
