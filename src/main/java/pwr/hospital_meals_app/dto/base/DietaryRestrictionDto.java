package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietaryRestrictionDto extends BaseGeneratedIdDto<Integer> {

    @NotNull
    @Size(max = 255)
    private String restriction;

    @NotNull
    private Integer patientId;

    @NotNull
    private Integer dietitianId;

    @NotNull
    private RestrictionStatusDto status;

}
