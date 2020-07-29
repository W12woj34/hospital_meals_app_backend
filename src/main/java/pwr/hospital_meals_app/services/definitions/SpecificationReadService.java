package pwr.hospital_meals_app.services.definitions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import pwr.hospital_meals_app.dto.base.PersistableDto;

import java.io.Serializable;
import java.util.List;

public interface SpecificationReadService<
        T extends PersistableDto<ID>, U extends Persistable<ID>, ID extends Serializable>
        extends ReadService<T, ID> {

    List<T> findAll(Specification<U> specification);

    Page<T> findAll(Specification<U> specification, Pageable pageable);

    List<T> findAll(Specification<U> specification, Sort sort);
}
