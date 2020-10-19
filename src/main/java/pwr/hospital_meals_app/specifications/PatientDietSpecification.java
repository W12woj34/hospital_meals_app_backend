package pwr.hospital_meals_app.specifications;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pwr.hospital_meals_app.persistance.entities.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
public class PatientDietSpecification implements Specification<PatientDietEntity> {

    private final Integer patientId;

    @Override
    public Predicate toPredicate(
            Root<PatientDietEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        if (patientId != null) {
            cq.where(
                    cb.and(
                            cb.equal(
                                    root.get(PatientDietEntity_.patient).get(PatientDietEntity_.id),
                                    patientId)));
        }

        return cq.getRestriction();
    }
}
