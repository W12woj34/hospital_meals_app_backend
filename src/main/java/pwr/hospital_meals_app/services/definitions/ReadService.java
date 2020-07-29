package pwr.hospital_meals_app.services.definitions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReadService<T, ID> {

    Page<T> findAll(Pageable pageable);

    List<T> findAll();

    boolean existsById(ID id);

    Optional<T> findById(ID id);

    long count();
}
