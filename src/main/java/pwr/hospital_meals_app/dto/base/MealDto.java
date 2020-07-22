package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pwr.hospital_meals_app.persistance.entities.BaseInheritedIdEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDto extends BaseInheritedIdEntity<Integer> {

    @Size(max = 255)
    private String additionalInfo;

    @NotNull
    private Integer option;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer typeId;

    @NotNull
    private Integer dietId;

}
