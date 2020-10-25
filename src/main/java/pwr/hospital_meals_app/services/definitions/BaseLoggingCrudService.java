package pwr.hospital_meals_app.services.definitions;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pwr.hospital_meals_app.dto.base.LogDto;
import pwr.hospital_meals_app.dto.base.PersistableDto;
import pwr.hospital_meals_app.persistance.repositories.*;
import pwr.hospital_meals_app.services.mappers.BaseMapper;
import pwr.hospital_meals_app.services.mappers.EventMapper;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Optional;

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
    OrderRepository orderRepository;
    PatientDietRepository patientDietRepository;
    StayRepository stayRepository;
    DietaryRestrictionRepository dietaryRestrictionRepository;
    private final EventMapper eventMapper;
    private final Integer createStatus;
    private final Integer updateStatus;

    @Value("${jwt-secret-key}")
    private String SECRET_AUTH;


    public BaseLoggingCrudService(R repository,
                                  BaseMapper<T, E> mapper,
                                  LoginRepository loginRepository,
                                  LogService logService,
                                  EventRepository eventRepository,
                                  OrderRepository orderRepository,
                                  PatientDietRepository patientDietRepository,
                                  StayRepository stayRepository,
                                  DietaryRestrictionRepository dietaryRestrictionRepository,
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
        this.orderRepository = orderRepository;
        this.patientDietRepository = patientDietRepository;
        this.stayRepository = stayRepository;
        this.dietaryRestrictionRepository = dietaryRestrictionRepository;
    }


    @Override
    public T saveAndLog(T dto, String token) {

        T dataEntity = save(dto);
        logService.save(createLogDto(dataEntity.getId(), getEmployeeIdFromToken(token), createStatus));
        return dataEntity;

    }


    @Override
    public Optional<T> updateByIdAndLog(T dto, ID id, String token) {

        Optional<T> dataEntity = updateById(dto, id);

        dataEntity.ifPresent(t -> logService.save(createLogDto(t.getId(), getEmployeeIdFromToken(token), updateStatus)));

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

        dto.setUserId(employeeId);
        if (status == 1 || status == 2) {
            dto.setTargetId(this.orderRepository.findById(Integer.valueOf(entityId.toString()))
                    .orElseThrow(EntityNotFoundException::new).getPatient().getPerson().getId());
        } else if (status == 3 || status == 4) {
            dto.setTargetId(this.dietaryRestrictionRepository.findById(Integer.valueOf(entityId.toString()))
                    .orElseThrow(EntityNotFoundException::new).getPatient().getPerson().getId());
        } else if (status == 5 || status == 6) {
            dto.setTargetId(this.stayRepository.findById(Integer.valueOf(entityId.toString()))
                    .orElseThrow(EntityNotFoundException::new).getPatient().getPerson().getId());
        } else if (status == 7) {
            dto.setTargetId(this.patientDietRepository.findById(Integer.valueOf(entityId.toString()))
                    .orElseThrow(EntityNotFoundException::new).getPatient().getPerson().getId());
        }
        //dto.setTargetId();
        dto.setEvent(eventRepository.findById(status).map(eventMapper::mapToDto)
                .orElseThrow(EntityNotFoundException::new));
        dto.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());

        return dto;

    }

}
