package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends BaseGeneratedIdDto<Integer> {

    @NotNull
    private Integer patientId;

    @NotNull
    private Integer nurseId;

    @NotNull
    private OrderStatusDto status;

    @NotNull
    private Timestamp timestamp;

}
