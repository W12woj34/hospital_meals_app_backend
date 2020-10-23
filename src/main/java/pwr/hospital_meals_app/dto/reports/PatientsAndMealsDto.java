package pwr.hospital_meals_app.dto.reports;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientsAndMealsDto {

    private Integer wardId;
    private String wardName;
    private Integer patients;
    private Integer breakfasts;
    private Integer lunches;
    private Integer suppers;
}
