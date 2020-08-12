package pwr.hospital_meals_app.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pwr.hospital_meals_app.persistance.entities.PatientEntity_;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.persistance.entities.StayEntity_;

@Data
public class StaySpecification implements Specification<StayEntity> {
    private final Integer patientId;

    @Override
    public Predicate toPredicate(
            Root<StayEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        if (patientId != null) {
            cq.where(
                    cb.and(
                            cb.equal(
                                    root.get(StayEntity_.patient).get(PatientEntity_.id),
                                    patientId)));
        }

        return cq.getRestriction();
    }
}
