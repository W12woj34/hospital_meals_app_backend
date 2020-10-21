package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto extends BaseInheritedIdDto<Integer> {

    @Size(max = 255)
    private String additionalInfo;

    @NotNull
    private PersonDto person;

}
