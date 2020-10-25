package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pwr.hospital_meals_app.dto.additionals.PatientDataDto;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.services.definitions.PatientService;
import pwr.hospital_meals_app.specifications.PatientSpecification;


@RestController
@RequestMapping(RestMappings.PATIENT)
@Api(tags = "Patients")
public class PatientController
        extends BaseSpecificationCrudController<
        PatientDto, Integer, PatientEntity, PatientSpecification> {

    private final PatientService service;

    public PatientController(PatientService service) {
        super(service);
        this.service = service;
    }

    @ApiOperation(value = "Get relevant patients data collected")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Page"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.DATA)
    public Page<PatientDataDto> getPatientsData(Pageable pageable) {
        return service.getPatientsData(pageable);
    }


    @ApiOperation(value = "Get relevant patient data collected by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.DATA + RestMappings.ID)
    public PatientDataDto getPatientData(@PathVariable Integer id) {
        return service.getPatientData(id);
    }

    @ApiOperation(value = "Get relevant patients data collected by ward id from token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.DATA_WARD)
    public Page<PatientDataDto> getPatientsDataWard(@RequestHeader("Authorization") String token) {
        return service.getPatientsDataFromWard(token);
    }

}
