package pwr.hospital_meals_app.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pwr.hospital_meals_app.persistance.entities.PatientEntity_;
import pwr.hospital_meals_app.persistance.entities.OrderEntity;
import pwr.hospital_meals_app.persistance.entities.OrderEntity_;

@Data
public class OrderSpecification implements Specification<OrderEntity> {
    private final Integer patientId;

    @Override
    public Predicate toPredicate(
            Root<OrderEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        if (patientId != null) {
            cq.where(
                    cb.and(
                            cb.equal(
                                    root.get(OrderEntity_.patient).get(PatientEntity_.id),
                                    patientId)));
        }

        return cq.getRestriction();
    }
}
