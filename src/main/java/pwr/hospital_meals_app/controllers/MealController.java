package pwr.hospital_meals_app.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.hospital_meals_app.dto.additionals.PatientMealOrderDto;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.persistance.entities.MealEntity;
import pwr.hospital_meals_app.services.definitions.MealService;
import pwr.hospital_meals_app.specifications.MealSpecification;

import java.util.List;

@RestController
@RequestMapping(RestMappings.MEAL)
public class MealController
        extends BaseSpecificationCrudController<
        MealDto, Integer, MealEntity, MealSpecification> {

    private final MealService service;

    public MealController(MealService service) {
        super(service);
        this.service = service;
    }


    @GetMapping(RestMappings.MEAL_ORDER)
    public Page<PatientMealOrderDto> getMealsOrderData(@RequestHeader("Authorization") String token) {
        return service.getPatientsOrders(token);
    }

    @GetMapping(RestMappings.MEAL_ORDER + RestMappings.ID)
    public Page<PatientMealOrderDto> getMealsOrderDataPatient(@RequestHeader("Authorization") String token,
                                                              @PathVariable Integer id) {
        return service.getPatientOrders(token, id);
    }


    @PostMapping(RestMappings.MEAL_ORDER)
    public ResponseEntity<Void> setMealsOrderData(@RequestHeader("Authorization") String token,
                                                  @RequestBody List<PatientMealOrderDto> dtos) {

        service.setPatientOrders(dtos, token);
        return ResponseEntity.ok().build();
    }
}
