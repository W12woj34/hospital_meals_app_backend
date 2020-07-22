package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends BaseGeneratedIdDto<Integer> {

    @NotNull
    private Integer patientId;

    @NotNull
    private Integer dietitianId;

    @NotNull
    private Integer statusId;

}
