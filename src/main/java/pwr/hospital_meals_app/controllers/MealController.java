package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.hospital_meals_app.dto.additionals.PatientMealOrderDto;
import pwr.hospital_meals_app.dto.base.DietDto;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.persistance.entities.MealEntity;
import pwr.hospital_meals_app.services.definitions.MealService;
import pwr.hospital_meals_app.specifications.MealSpecification;

import java.util.List;

@RestController
@RequestMapping(RestMappings.MEAL)
@Api(tags = "Meals")
public class MealController
        extends BaseSpecificationCrudController<
        MealDto, Integer, MealEntity, MealSpecification> {

    private final MealService service;

    public MealController(MealService service) {
        super(service);
        this.service = service;
    }


    @ApiOperation(value = "Get data with patients orders")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Page"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.MEAL_ORDER)
    public Page<PatientMealOrderDto> getMealsOrderData(@RequestHeader("Authorization") String token) {
        return service.getPatientsOrders(token);
    }

    @ApiOperation(value = "Get data with patient orders by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Page"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.MEAL_ORDER + RestMappings.ID)
    public Page<PatientMealOrderDto> getMealsOrderDataPatient(@RequestHeader("Authorization") String token,
                                                              @PathVariable Integer id) {
        return service.getPatientOrders(token, id);
    }

    @ApiOperation(value = "Post data with patient orders")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping(RestMappings.MEAL_ORDER)
    public ResponseEntity<Void> setMealsOrderData(@RequestHeader("Authorization") String token,
                                                  @RequestBody List<PatientMealOrderDto> dtos) {

        service.setPatientOrders(dtos, token);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Change today patient meals diet by patient id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PutMapping(RestMappings.MEAL_ORDER + RestMappings.ID)
    public ResponseEntity<Void> setPatientMealsDiet(@PathVariable Integer id,
                                                    @RequestBody DietDto dto) {

        service.setPatientMealsDiet(id, dto);
        return ResponseEntity.ok().build();
    }
}
