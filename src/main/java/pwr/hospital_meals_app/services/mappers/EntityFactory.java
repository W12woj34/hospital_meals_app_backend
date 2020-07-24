package pwr.hospital_meals_app.services.mappers;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;
import pwr.hospital_meals_app.dto.base.PersistableDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Class used by MapStruct, responsible for delivery entity object
 */
@Component
@Slf4j
public class EntityFactory {

    @PersistenceContext
    private EntityManager em;

    /**
     * Method which return entity object based on, if entity exist in database with id visible in dto
     *
     * @param dto         Dto object, with data
     * @param entityClass Entity class
     * @param <T>         Entity type
     */
    @ObjectFactory
    public <T extends Persistable<?>> T resolveEntity(
            PersistableDto<?> dto, @TargetType Class<T> entityClass) {
        T entity = null;

        if (dto.getId() != null) {
            entity = em.find(entityClass, dto.getId());
        }

        if (entity == null) {
            try {
                entity = entityClass.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                log.error(e.getMessage());
            }
        }

        return entity;
    }
}
