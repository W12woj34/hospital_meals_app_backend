package pwr.hospital_meals_app.services.definitions;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pwr.hospital_meals_app.dto.base.LogDto;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.persistance.repositories.EventRepository;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.services.mappers.BaseMapper;
import pwr.hospital_meals_app.services.mappers.EventMapper;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Optional;

import static pwr.hospital_meals_app.security.SecurityConstants.SECRET_AUTH;
import static pwr.hospital_meals_app.security.SecurityConstants.TOKEN_PREFIX;

/**
 * Base class for service supporting CRUD with database using mapping between dto and entities
 *
 * @param <T>  DTO type
 * @param <E>  Entity type
 * @param <ID> Entity ID type
 */
public abstract class BaseLoggingCrudService<
        T extends PersistableDto<ID>,
        E extends Persistable<ID>,
        ID extends Serializable,
        R extends JpaRepository<E, ID> & JpaSpecificationExecutor<E>>
        extends BaseSpecificationCrudService<T, E, ID, R> implements LoggingCrudService<T, E, ID> {

    private final LoginRepository loginRepository;
    private final LogService logService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final Integer createStatus;
    private final Integer updateStatus;


    public BaseLoggingCrudService(R repository,
                                  BaseMapper<T, E> mapper,
                                  LoginRepository loginRepository,
                                  LogService logService,
                                  EventRepository eventRepository,
                                  EventMapper eventMapper,
                                  Integer createStatus,
                                  Integer updateStatus) {

        super(repository, mapper);
        this.loginRepository = loginRepository;
        this.logService = logService;
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.createStatus = createStatus;
        this.updateStatus = updateStatus;
    }


   @Override
    public T saveAndLog(T dto, String token) {

        T dataEntity = save(dto);
        logService.save(createLogDto(dto.getId(), getEmployeeIdFromToken(token), createStatus));
        return dataEntity;

    }


    @Override
    public Optional<T> updateByIdAndLog(T dto, ID id, String token) {

        Optional<T> dataEntity = updateById(dto, id);

        if (dataEntity.isPresent()) {
            logService.save(createLogDto(dto.getId(), getEmployeeIdFromToken(token), updateStatus));
        }

        return dataEntity;
    }

    private Integer getEmployeeIdFromToken(String token) {

        Jws<Claims> claimsJws =
                Jwts.parser()
                        .setSigningKey(SECRET_AUTH.getBytes())
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

        return Optional.ofNullable(loginRepository
                .findByUsername(claimsJws.getBody().getSubject()).getEmployee().getId())
                .orElseThrow(EntityNotFoundException::new);

    }

    private LogDto createLogDto(ID entityId, Integer employeeId, Integer status) {

        LogDto dto = new LogDto();

        dto.setModifiedEntityId(Integer.valueOf(entityId.toString()));
        dto.setUserId(employeeId);
        dto.setEvent(eventRepository.findById(status).map(eventMapper::mapToDto)
                .orElseThrow(EntityNotFoundException::new));
        dto.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return dto;

    }

}
