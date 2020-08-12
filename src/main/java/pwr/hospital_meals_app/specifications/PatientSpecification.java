package pwr.hospital_meals_app.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;

@And({
        @Spec(path = "firstName", params = "firstName", spec = Like.class),
        @Spec(path = "lastName", params = "lastName", spec = Like.class),
        @Spec(
                path = "birthdate",
                params = {"birthdateFrom", "birthdateTo"},
                spec = Between.class),
        @Spec(path = "pesel", params = "pesel", spec = Like.class),
})
public interface PatientSpecification extends Specification<PatientEntity> {}
