package pwr.hospital_meals_app.services.definitions;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pwr.hospital_meals_app.dto.additionals.EmployeeDataDto;
import pwr.hospital_meals_app.dto.base.EmployeeDto;

import java.util.List;

public interface EmployeeService extends PersonalDataService<EmployeeDto> {

    Page<EmployeeDataDto> getEmployeesData(Pageable pageable);

    List<EmployeeDataDto> getEmployeesData();

    EmployeeDataDto getEmployeeData(Integer id);

    EmployeeDataDto getEmployeeDataPersonal(String token);
}
