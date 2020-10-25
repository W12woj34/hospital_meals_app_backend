package pwr.hospital_meals_app.services.definitions;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.services.mappers.BaseMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static pwr.hospital_meals_app.security.SecurityConstants.TOKEN_PREFIX;

/**
 * Base class for service which extends CRUD specification service and log specific user actions in database
 *
 * @param <T>  DTO type
 * @param <E>  Entity type
 */
public abstract class BasePersonalCrudService<
        T extends PersistableDto<Integer>,
        E extends Persistable<Integer>,
        R extends JpaRepository<E, Integer>>
        extends BaseCrudService<T, E, Integer, R> implements PersonalDataService<T> {

    private final LoginRepository loginRepository;

    @Value("${jwt-secret-key}")
    private String SECRET_AUTH;

    public BasePersonalCrudService(R repository, BaseMapper<T, E> mapper, LoginRepository loginRepository) {
        super(repository, mapper);
        this.loginRepository = loginRepository;
    }

    @Override
    public Optional<T> getPersonal(String token) {

        Jws<Claims> claimsJws =
                Jwts.parser()
                        .setSigningKey(SECRET_AUTH.getBytes())
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

        Integer id = Optional.ofNullable(loginRepository
                .findByUsername(claimsJws.getBody().getSubject()).getEmployee().getId())
                .orElseThrow(EntityNotFoundException::new);

        Optional<E> entityOptional = repository.findById(id);

        return entityOptional.map(mapper::mapToDto);
    }
}
