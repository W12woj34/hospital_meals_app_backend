package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDto extends BaseGeneratedIdDto<Integer> {

    @NotNull
    private String timestamp;
    
    @NotNull
    private Integer userId;

    @NotNull
    private Integer targetId;

    @NotNull
    private EventDto event;

}
