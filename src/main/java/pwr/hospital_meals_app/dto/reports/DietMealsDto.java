package pwr.hospital_meals_app.dto.reports;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietMealsDto {

    private String dietName;
    private Integer breakfasts;
    private Integer lunches;
    private Integer suppers;

}
