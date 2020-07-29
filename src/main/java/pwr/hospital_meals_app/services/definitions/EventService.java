package pwr.hospital_meals_app.services.definitions;


import pwr.hospital_meals_app.dto.base.EventDto;
import pwr.hospital_meals_app.persistance.entities.EventEntity;

public interface EventService extends SpecificationCrudService<EventDto, EventEntity, Integer> {
}