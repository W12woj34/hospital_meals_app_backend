package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.EmployeeDto;
import pwr.hospital_meals_app.persistance.entities.EmployeeEntity;

public interface EmployeeService extends SpecificationCrudService<EmployeeDto, EmployeeEntity, Integer> {
}