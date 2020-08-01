package pwr.hospital_meals_app.dto.additionals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pwr.hospital_meals_app.dto.base.BaseGeneratedIdDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDto extends BaseGeneratedIdDto<Integer> {

    @NotNull
    @Size(max = 30)
    private String oldPassword;

    @NotNull
    @Size(max = 30)
    private String newPassword;

}
