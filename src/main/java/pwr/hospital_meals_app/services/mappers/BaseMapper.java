package pwr.hospital_meals_app.services.mappers;


import java.util.Collection;
import java.util.List;

public interface BaseMapper<D, E> {

    D mapToDto(E entity);

    E mapToEntity(D dto);

    List<D> mapToDtoList(Collection<E> entity);

    List<E> mapToEntityList(Collection<D> dto);
}
