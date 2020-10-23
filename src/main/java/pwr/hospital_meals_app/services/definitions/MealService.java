package pwr.hospital_meals_app.services.definitions;


import org.springframework.data.domain.Page;
import pwr.hospital_meals_app.dto.additionals.PatientMealOrderDto;
import pwr.hospital_meals_app.dto.base.DietDto;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.persistance.entities.MealEntity;

import java.util.List;

public interface MealService extends SpecificationCrudService<MealDto, MealEntity, Integer> {

    Page<PatientMealOrderDto> getPatientOrders(String token, Integer id);

    void setPatientOrders(List<PatientMealOrderDto> orders, String token);

    Page<PatientMealOrderDto> getPatientsOrders(String token);

    void setPatientMealsDiet(Integer patientId, DietDto diet);
}
