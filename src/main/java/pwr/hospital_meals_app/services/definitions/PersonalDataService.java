package pwr.hospital_meals_app.services.definitions;

import java.util.Optional;

public interface PersonalDataService<T> extends CrudService<T, Integer>{

    Optional<T> getPersonal(String token);
}
