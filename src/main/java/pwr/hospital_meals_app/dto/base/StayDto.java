package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StayDto extends BaseGeneratedIdDto<Integer> {

    @NotNull
    private Integer patientId;

    @NotNull
    @PastOrPresent
    private LocalDate admissionDate;

    private LocalDate releaseDate;

    @NotNull
    private boolean archived;

    @NotNull
    private Integer wardId;

}
