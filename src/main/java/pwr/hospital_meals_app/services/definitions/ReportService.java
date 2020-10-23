package pwr.hospital_meals_app.services.definitions;

import java.time.LocalDate;

public interface ReportService {

    void generatePatientsAndMealsReport(LocalDate date);

    void generateSummaryMealsReport();
}
