package pwr.hospital_meals_app.controllers;

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

    MealService service;

    public MealController(MealService service) {
        super(service);
    }


    @GetMapping(RestMappings.MEAL_ORDER + RestMappings.ID)
    public List<PatientMealOrderDto> getMealsOrderData(@PathVariable Integer id) {
        return service.getPatientOrders(id);
    }

    @PostMapping(RestMappings.MEAL_ORDER)
    public ResponseEntity<Void> setMealsOrderData(@RequestBody List<PatientMealOrderDto> dtos) {

        service.setPatientOrders(dtos);
        return ResponseEntity.ok().build();
    }
}
