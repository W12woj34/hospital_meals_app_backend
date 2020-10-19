package pwr.hospital_meals_app.dto.additionals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pwr.hospital_meals_app.dto.groups.OnCreate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDataDto {

    @Null(groups = {OnCreate.class})
    private Integer id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @PastOrPresent
    private LocalDate birthDate;

    @NotNull
    @Digits(integer = 11, fraction = 0)
    private Long pesel;

    @NotNull
    @Size(max = 255)
    private String ward;

    @NotNull
    @Size(max = 255)
    private String diet;

    @Size(max = 255)
    private String additionalInfo;

    @PastOrPresent
    private LocalDate admissionDate;

}
