package pwr.hospital_meals_app.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pwr.hospital_meals_app.persistance.entities.LogEntity;
import pwr.hospital_meals_app.persistance.entities.MealEntity;

@And({
        @Spec(path = "option", params = "option", spec = Equal.class),
        @Spec(
                path = "date",
                params = {"dateFrom", "dateTo"},
                spec = Between.class),
})
public interface MealSpecification extends Specification<MealEntity> {
}