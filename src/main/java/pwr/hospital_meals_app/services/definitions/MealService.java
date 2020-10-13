package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.additionals.PatientMealOrderDto;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.persistance.entities.MealEntity;

import java.util.List;

public interface MealService extends SpecificationCrudService<MealDto, MealEntity, Integer> {

    List<PatientMealOrderDto> getPatientOrders(Integer ward);

    void setPatientOrders(List<PatientMealOrderDto> order);
}
