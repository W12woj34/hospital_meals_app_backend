package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDietDto extends BaseGeneratedIdDto<Integer> {

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Integer patientId;

    @NotNull
    private DietDto diet;

}
