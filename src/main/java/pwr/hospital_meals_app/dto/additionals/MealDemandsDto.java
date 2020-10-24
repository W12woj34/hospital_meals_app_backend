package pwr.hospital_meals_app.dto.additionals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDemandsDto {

    private Integer breakfasts;
    private Integer lunches;
    private Integer suppers;
    private String lastUpdate;
}
