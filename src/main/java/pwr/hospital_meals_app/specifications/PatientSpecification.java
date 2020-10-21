package pwr.hospital_meals_app.specifications;

import org.springframework.data.jpa.domain.Specification;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;

public interface PatientSpecification extends Specification<PatientEntity> {}
