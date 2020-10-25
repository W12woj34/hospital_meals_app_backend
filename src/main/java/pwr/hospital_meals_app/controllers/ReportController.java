package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = "Reports")
public class ReportController {

    private final ReportService service;


    @ApiOperation(value = "Get meals number and last modification date")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.MEAL)
    public MealDemandsDto getMealDemands() {

        return service.getMealDemands();

    }

    @ApiOperation(value = "Get reports with patients and meals number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.REPORT_CONTROL)
    public ResponseEntity<Resource> getPatientsAndMealsReport(@RequestParam String date) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        Resource resource = service.generatePatientsAndMealsReport(LocalDate.parse(date));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }

    @ApiOperation(value = "Get reports with meals summary")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.REPORT_SUMMARY)
    public ResponseEntity<Resource> getSummaryMealsReport() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        Resource resource = service.generateSummaryMealsReport();
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Get reports with meals demands from all wards")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.REPORT_DEMANDS)
    public ResponseEntity<Resource> getTodayMealsDemand() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        Resource resource = service.generateTodayMealsDemand();
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


}
