package pwr.hospital_meals_app.services.definitions;

import org.springframework.data.domain.Persistable;
import pwr.hospital_meals_app.dto.base.PersistableDto;

import java.io.Serializable;
import java.util.Optional;

public interface LoggingCrudService<T extends PersistableDto<ID>, U extends Persistable<ID>, ID extends Serializable>
        extends SpecificationCrudService<T, U, ID> {

    T saveAndLog(T dto, String token);

    Optional<T> updateByIdAndLog(T dto, ID id, String token);

}
