package pwr.hospital_meals_app.services.definitions;

import org.springframework.core.io.Resource;
import pwr.hospital_meals_app.dto.additionals.MealDemandsDto;

import java.time.LocalDate;

public interface ReportService {

    Resource generatePatientsAndMealsReport(LocalDate date);

    Resource generateSummaryMealsReport();

    Resource generateTodayMealsDemand();

    MealDemandsDto getMealDemands();
}
