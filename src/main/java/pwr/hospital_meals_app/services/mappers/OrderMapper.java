package pwr.hospital_meals_app.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pwr.hospital_meals_app.dto.base.OrderDto;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.persistance.entities.OrderStatusEntity;
import pwr.hospital_meals_app.persistance.entities.OrderEntity;
import pwr.hospital_meals_app.persistance.repositories.PatientRepository;
import pwr.hospital_meals_app.persistance.repositories.OrderStatusRepository;

import javax.persistence.EntityNotFoundException;

@Mapper(uses = {EntityFactory.class})
public abstract class OrderMapper
        implements BaseMapper<OrderDto, OrderEntity> {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Override
    @Mapping(target = "patientId", source = "patient.id")
    public abstract OrderDto mapToDto(OrderEntity entity);

    @Override
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "status", source = "status.id")
    public abstract OrderEntity mapToEntity(OrderDto dto);

    protected PatientEntity patientEntityFromId(Integer id) {
        return patientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    protected OrderStatusEntity OrderStatusEntityFromId(Integer id) {
        return orderStatusRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
