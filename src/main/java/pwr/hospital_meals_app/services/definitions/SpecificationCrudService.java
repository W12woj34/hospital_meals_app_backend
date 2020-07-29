package pwr.hospital_meals_app.services.definitions;

import org.springframework.data.domain.Persistable;
import pwr.hospital_meals_app.dto.base.PersistableDto;

import java.io.Serializable;

public interface SpecificationCrudService<
        T extends PersistableDto<ID>, U extends Persistable<ID>, ID extends Serializable>
        extends CrudService<T, ID>, SpecificationReadService<T, U, ID> {
}
