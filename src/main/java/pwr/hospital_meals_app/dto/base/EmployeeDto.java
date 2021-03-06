package pwr.hospital_meals_app.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pwr.hospital_meals_app.dto.groups.OnCreate;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto extends BaseInheritedIdDto<Integer> {

    @NotNull(groups = OnCreate.class)
    private Integer loginId;

    @NotNull
    private PersonDto person;

}
