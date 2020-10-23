package pwr.hospital_meals_app.dto.reports;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryMealsDto {

    private Integer wardId;
    private String wardName;
    private Integer strict;
    private Integer lightAndOthers;
    private Integer basic;
}
