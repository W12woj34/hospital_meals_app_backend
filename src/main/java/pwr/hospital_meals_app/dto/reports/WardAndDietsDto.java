package pwr.hospital_meals_app.dto.reports;

import lombok.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardAndDietsDto {

    private Integer wardId;
    private String wardName;
    private List<DietMealsDto> dietMealsList;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private JRBeanCollectionDataSource dietMealsDataSource;

    public JRBeanCollectionDataSource getDietMealsDataSource() {
        return new JRBeanCollectionDataSource(dietMealsList, false);
    }

}
