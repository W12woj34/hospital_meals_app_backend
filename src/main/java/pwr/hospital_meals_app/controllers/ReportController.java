package pwr.hospital_meals_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pwr.hospital_meals_app.dto.additionals.MealDemandsDto;
import pwr.hospital_meals_app.services.definitions.ReportService;

import java.time.LocalDate;


/**
 * Controller used to handle report requests
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(RestMappings.REPORT)
public class ReportController {

    private final ReportService service;

    @GetMapping(RestMappings.MEAL)
    public MealDemandsDto getMealDemands() {

        return service.getMealDemands();

    }

    @GetMapping(RestMappings.REPORT_CONTROL)
    public ResponseEntity<Resource> getPatientsAndMealsReport(@RequestParam String date) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        Resource resource = service.generatePatientsAndMealsReport(LocalDate.parse(date));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }

    @GetMapping(RestMappings.REPORT_SUMMARY)
    public ResponseEntity<Resource> getSummaryMealsReport() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        Resource resource = service.generateSummaryMealsReport();
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping(RestMappings.REPORT_DEMANDS)
    public ResponseEntity<Resource> getTodayMealsDemand() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        Resource resource = service.generateTodayMealsDemand();
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


}
