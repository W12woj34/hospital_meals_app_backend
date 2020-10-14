package pwr.hospital_meals_app.services.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.additionals.PatientMealOrderDto;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.dto.base.MealTypeDto;
import pwr.hospital_meals_app.dto.base.OrderDto;
import pwr.hospital_meals_app.persistance.entities.*;
import pwr.hospital_meals_app.persistance.repositories.*;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.MealService;
import pwr.hospital_meals_app.services.mappers.*;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pwr.hospital_meals_app.security.SecurityConstants.SECRET_AUTH;
import static pwr.hospital_meals_app.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class MealServiceImpl
        extends BaseSpecificationCrudService<MealDto, MealEntity, Integer, MealRepository>
        implements MealService {


    private final PatientRepository patientRepository;
    private final StayRepository stayRepository;
    private final WardRepository wardRepository;
    private final WardNurseRepository wardNurseRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusMapper orderStatusMapper;
    private final OrderMapper orderMapper;
    private final DietMapper dietMapper;
    private final MealMapper mealMapper;
    private final MealRepository mealRepository;
    private final MealTypeMapper mealTypeMapper;
    private final MealTypeRepository mealTypeRepository;
    private final LoginRepository loginRepository;

    public MealServiceImpl(MealRepository repository,
                           MealMapper mapper,
                           PatientRepository patientRepository,
                           StayRepository stayRepository,
                           WardRepository wardRepository,
                           WardNurseRepository wardNurseRepository,
                           OrderStatusRepository orderStatusRepository,
                           OrderRepository orderRepository,
                           OrderStatusMapper orderStatusMapper,
                           OrderMapper orderMapper,
                           DietMapper dietMapper,
                           MealMapper mealMapper,
                           MealRepository mealRepository, MealTypeMapper mealTypeMapper,
                           MealTypeRepository mealTypeRepository,
                           LoginRepository loginRepository) {

        super(repository, mapper);
        this.patientRepository = patientRepository;
        this.stayRepository = stayRepository;
        this.wardRepository = wardRepository;
        this.wardNurseRepository = wardNurseRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderRepository = orderRepository;
        this.orderStatusMapper = orderStatusMapper;
        this.orderMapper = orderMapper;
        this.dietMapper = dietMapper;
        this.mealMapper = mealMapper;
        this.mealRepository = mealRepository;
        this.mealTypeMapper = mealTypeMapper;
        this.mealTypeRepository = mealTypeRepository;
        this.loginRepository = loginRepository;
    }

    @Override
    public List<PatientMealOrderDto> getPatientOrders(Integer ward) {

        List<PatientMealOrderDto> dtos = new LinkedList<>();
        List<StayEntity> stays = stayRepository
                .findByArchivedAndWard(false,
                        wardRepository.findById(ward).orElseThrow(EntityNotFoundException::new));

        List<PatientEntity> patients = stays.stream()
                .map(StayEntity::getPatient).collect(Collectors.toList());

        for (PatientEntity patient : patients) {
            dtos.add(createPatientMealOrderDto(patient));
        }

        return dtos;
    }

    @Override
    public void setPatientOrders(List<PatientMealOrderDto> orders, String token) {

        Integer nurseId = resolveIdFromToken(token);
        for (PatientMealOrderDto order : orders) {
            savePatientMealOrderDto(order, nurseId);
        }
    }

    private Integer resolveIdFromToken(String token) {

        Jws<Claims> claimsJws =
                Jwts.parser()
                        .setSigningKey(SECRET_AUTH.getBytes())
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

        return Optional.ofNullable(loginRepository
                .findByUsername(claimsJws.getBody().getSubject()).getEmployee().getId())
                .orElseThrow(EntityNotFoundException::new);
    }

    private PatientMealOrderDto createPatientMealOrderDto(PatientEntity patient) {

        PatientMealOrderDto dto = new PatientMealOrderDto();

        dto.setId(patient.getId());
        dto.setFirstName(patient.getLastName());
        dto.setLastName(patient.getLastName());
        dto.setBirthDate(patient.getBirthDate());
        dto.setPesel(patient.getPesel());

        List<MealEntity> patientMeals = patient.getOrders().stream()
                .map(OrderEntity::getMeal)
                .filter(m -> m.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());


        dto.setBreakfast(false);
        dto.setLunch(false);
        dto.setSupper(false);
        for (MealEntity patientMeal : patientMeals) {

            if (patientMeal.getType().getName()
                    .equals("Śniadanie")) {
                dto.setBreakfast(!patientMeal.getOrder().getStatus().getName().equals("Anulowane"));
            }

            if (patientMeal.getType().getName()
                    .equals("Obiad")) {
                dto.setLunch(!patientMeal.getOrder().getStatus().getName().equals("Anulowane"));
            }

            if (patientMeal.getType().getName()
                    .equals("Kolacja")) {
                dto.setSupper(!patientMeal.getOrder().getStatus().getName().equals("Anulowane"));
            }
        }

        return dto;
    }

    private void savePatientMealOrderDto(PatientMealOrderDto order, Integer nurseId) {


        PatientEntity patient = patientRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        List<MealEntity> patientMeals = patient
                .getOrders().stream()
                .map(OrderEntity::getMeal)
                .filter(m -> m.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());


        saveDtoToDatabase(nurseId, order, "Śniadanie", patient, patientMeals);
        saveDtoToDatabase(nurseId, order, "Obiad", patient, patientMeals);
        saveDtoToDatabase(nurseId, order, "Kolacja", patient, patientMeals);
    }

    private void saveDtoToDatabase(Integer nurseId,
                                   PatientMealOrderDto order,
                                   String mealTypeName,
                                   PatientEntity patient,
                                   List<MealEntity> patientMeals) {

        Optional<MealEntity> mealType = patientMeals.stream()
                .filter(m -> m.getType().getName().equals(mealTypeName))
                .findFirst();

        if (mealType.isPresent()) {
            OrderEntity breakfastOrder = mealType.get().getOrder();
            breakfastOrder.setNurse(wardNurseRepository.findById(nurseId)
                    .orElseThrow(EntityNotFoundException::new));
            breakfastOrder.setTimestamp(new Timestamp(System.currentTimeMillis()));
            if (order.isBreakfast()) {
                breakfastOrder.setStatus(orderStatusRepository.findById(3).orElseThrow(EntityNotFoundException::new));
            } else {
                breakfastOrder.setStatus(orderStatusRepository.findById(4).orElseThrow(EntityNotFoundException::new));
            }
            orderRepository.save(breakfastOrder);
        } else {

            OrderDto orderDto = new OrderDto();
            orderDto.setNurseId(nurseId);
            orderDto.setPatientId(patient.getId());
            orderDto.setStatus(orderStatusRepository.findById(1).stream()
                    .map(orderStatusMapper::mapToDto).findFirst()
                    .orElseThrow(EntityNotFoundException::new));
            orderDto.setTimestamp(new Timestamp(System.currentTimeMillis()));
            orderDto = orderMapper.mapToDto(orderRepository.save(orderMapper.mapToEntity(orderDto)));
            MealDto mealDto = new MealDto();
            mealDto.setDate(LocalDate.now());

            mealDto.setDiet(dietMapper.mapToDto(patient.getPatientDiets().stream()
                    .filter(pd -> pd.getEndDate() == null)
                    .findFirst().orElseThrow(EntityNotFoundException::new)
                    .getDiet()));
            MealTypeDto mealTypeDto = mealTypeMapper.mapToDto(mealTypeRepository.findById(1).orElseThrow(EntityNotFoundException::new));
            mealDto.setType(mealTypeDto);
            mealDto.setId(orderDto.getId());
            mealRepository.save(mealMapper.mapToEntity(mealDto));
        }
    }
}
