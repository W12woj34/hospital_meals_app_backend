package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDto extends BaseGeneratedIdDto<Integer> {

    @NotNull
    private Timestamp timestamp;

    @NotNull
    private Integer modifiedEntityId;

    @NotNull
    private Integer userId;

    @NotNull
    private EventDto event;

}
