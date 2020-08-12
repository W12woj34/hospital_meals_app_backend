package pwr.hospital_meals_app.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pwr.hospital_meals_app.persistance.entities.LogEntity;

@And({
        @Spec(
                path = "timestamp",
                params = {"timestampFrom", "timestampTo"},
                spec = Between.class)
})
public interface LogSpecification extends Specification<LogEntity> {
}