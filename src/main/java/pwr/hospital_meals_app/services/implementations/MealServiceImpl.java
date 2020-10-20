package pwr.hospital_meals_app.services.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.hospital_meals_app.dto.additionals.PatientMealOrderDto;
import pwr.hospital_meals_app.dto.base.MealDto;
import pwr.hospital_meals_app.dto.base.MealTypeDto;
import pwr.hospital_meals_app.dto.base.OrderDto;
import pwr.hospital_meals_app.persistance.entities.*;
import pwr.hospital_meals_app.persistance.repositories.*;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.MealService;
import pwr.hospital_meals_app.services.definitions.OrderService;
import pwr.hospital_meals_app.services.mappers.*;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
    private final OrderStatusMapper orderStatusMapper;
    private final OrderMapper orderMapper;
    private final DietMapper dietMapper;
    private final MealMapper mealMapper;
    private final MealRepository mealRepository;
    private final MealTypeMapper mealTypeMapper;
    private final MealTypeRepository mealTypeRepository;
    private final LoginRepository loginRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final DietRepository dietRepository;

    public MealServiceImpl(MealRepository repository,
                           MealMapper mapper,
                           PatientRepository patientRepository,
                           StayRepository stayRepository,
                           WardRepository wardRepository,
                           WardNurseRepository wardNurseRepository,
                           OrderStatusRepository orderStatusRepository,
                           OrderStatusMapper orderStatusMapper,
                           OrderMapper orderMapper,
                           DietMapper dietMapper,
                           MealMapper mealMapper,
                           MealRepository mealRepository,
                           MealTypeMapper mealTypeMapper,
                           MealTypeRepository mealTypeRepository,
                           LoginRepository loginRepository,
                           OrderService orderService,
                           OrderRepository orderRepository, DietRepository dietRepository) {

        super(repository, mapper);
        this.patientRepository = patientRepository;
        this.stayRepository = stayRepository;
        this.wardRepository = wardRepository;
        this.wardNurseRepository = wardNurseRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusMapper = orderStatusMapper;
        this.orderMapper = orderMapper;
        this.dietMapper = dietMapper;
        this.mealMapper = mealMapper;
        this.mealRepository = mealRepository;
        this.mealTypeMapper = mealTypeMapper;
        this.mealTypeRepository = mealTypeRepository;
        this.loginRepository = loginRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.dietRepository = dietRepository;
    }

    @Override
    public Page<PatientMealOrderDto> getPatientOrders(String token, Integer id) {

        Integer ward = resolveWardIdFromToken(token);
        List<PatientMealOrderDto> dtos = new LinkedList<>();
        List<StayEntity> stays = stayRepository
                .findByArchivedAndWard(false,
                        wardRepository.findById(ward).orElseThrow(EntityNotFoundException::new));

        List<PatientEntity> patients = stays.stream()
                .map(StayEntity::getPatient)
                .filter(p -> Objects.equals(p.getId(), id)).collect(Collectors.toList());

        for (PatientEntity patient : patients) {
            dtos.add(createPatientMealOrderDto(patient));
        }

        return new PageImpl<>(dtos);
    }

    @Override
    @Transactional
    public void setPatientOrders(List<PatientMealOrderDto> orders, String token) {

        Integer nurseId = resolveIdFromToken(token);
        for (PatientMealOrderDto order : orders) {
            savePatientMealOrderDto(order, nurseId, token);
        }
    }

    @Override
    public Page<PatientMealOrderDto> getPatientsOrders(String token) {

        Integer ward = resolveWardIdFromToken(token);
        List<PatientMealOrderDto> dtos = new LinkedList<>();
        List<StayEntity> stays = stayRepository
                .findByArchivedAndWard(false,
                        wardRepository.findById(ward).orElseThrow(EntityNotFoundException::new));

        List<PatientEntity> patients = stays.stream()
                .map(StayEntity::getPatient).collect(Collectors.toList());

        for (PatientEntity patient : patients) {
            dtos.add(createPatientMealOrderDto(patient));
        }

        return new PageImpl<>(dtos);
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

    private Integer resolveWardIdFromToken(String token) {

        Jws<Claims> claimsJws =
                Jwts.parser()
                        .setSigningKey(SECRET_AUTH.getBytes())
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));


        Integer nurseId = Optional.ofNullable(loginRepository
                .findByUsername(claimsJws.getBody().getSubject()).getEmployee().getId())
                .orElseThrow(EntityNotFoundException::new);

        return wardNurseRepository.findById(nurseId)
                .orElseThrow(EntityNotFoundException::new)
                .getWard()
                .getId();
    }

    private PatientMealOrderDto createPatientMealOrderDto(PatientEntity patient) {

        PatientMealOrderDto dto = new PatientMealOrderDto();

        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setBirthDate(patient.getBirthDate());
        dto.setPesel(patient.getPesel());

        dto.setWard(patient.getStays().stream()
                .filter(s -> !s.isArchived() && s.getReleaseDate() == null)
                .map(s -> s.getWard().getName())
                .findFirst()
                .orElseThrow(EntityNotFoundException::new)
        );


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

    private void savePatientMealOrderDto(PatientMealOrderDto order, Integer nurseId, String token) {


        PatientEntity patient = patientRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        List<MealEntity> patientMeals = patient
                .getOrders().stream()
                .map(OrderEntity::getMeal)
                .filter(m -> m.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());


        saveDtoToDatabase(nurseId, order, "Śniadanie", patient, patientMeals, token);
        saveDtoToDatabase(nurseId, order, "Obiad", patient, patientMeals, token);
        saveDtoToDatabase(nurseId, order, "Kolacja", patient, patientMeals, token);
    }

    private void saveDtoToDatabase(Integer nurseId,
                                   PatientMealOrderDto order,
                                   String mealTypeName,
                                   PatientEntity patient,
                                   List<MealEntity> patientMeals,
                                   String token) {

        Optional<MealEntity> mealType = patientMeals.stream()
                .filter(m -> m.getType().getName().equals(mealTypeName))
                .findFirst();

        if (mealType.isPresent()) {
            OrderEntity mealOrder = mealType.get().getOrder();
            mealOrder.setNurse(wardNurseRepository.findById(nurseId)
                    .orElseThrow(EntityNotFoundException::new));
            mealOrder.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
            if ((order.isBreakfast() && mealTypeName.equals("Śniadanie")) ||
                    (order.isLunch() && mealTypeName.equals("Obiad")) ||
                    (order.isSupper() && mealTypeName.equals("Kolacja"))) {
                mealOrder.setStatus(orderStatusRepository.findById(3).orElseThrow(EntityNotFoundException::new));
            } else {
                mealOrder.setStatus(orderStatusRepository.findById(4).orElseThrow(EntityNotFoundException::new));
            }
            orderService.updateByIdAndLog(orderMapper.mapToDto(mealOrder), mealOrder.getId(), token);

        } else {

            if ((!order.isBreakfast() && mealTypeName.equals("Śniadanie")) ||
                    (!order.isLunch() && mealTypeName.equals("Obiad")) ||
                    (!order.isSupper() && mealTypeName.equals("Kolacja"))) {
                return;
            }

            OrderDto orderDto = new OrderDto();
            orderDto.setNurseId(nurseId);
            orderDto.setPatientId(patient.getId());
            orderDto.setStatus(orderStatusRepository.findById(1).stream()
                    .map(orderStatusMapper::mapToDto).findFirst()
                    .orElseThrow(EntityNotFoundException::new));
            orderDto.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
            orderDto = orderService.saveAndLog(orderDto, token);
            MealDto mealDto = new MealDto();
            mealDto.setDate(LocalDate.now());


            Optional<PatientDietEntity> patientDiet = patient.getPatientDiets().stream()
                    .filter(pd -> pd.getEndDate() == null).findFirst();
            if (patientDiet.isPresent()) {
                mealDto.setDiet(dietMapper
                        .mapToDto(patientDiet.get().getDiet()));
            } else {
                mealDto.setDiet(dietMapper
                        .mapToDto(dietRepository.findById(1)
                                .orElseThrow(EntityNotFoundException::new)));
            }


            MealTypeDto mealTypeDto = mealTypeMapper.mapToDto(mealTypeRepository.findByName(mealTypeName));
            mealDto.setType(mealTypeDto);
            mealDto.setAdditionalInfo("");
            MealEntity mealEntity = mealMapper.mapToEntity(mealDto);
            mealEntity.setOrder(orderRepository.findById(orderDto.getId())
                    .orElseThrow(EntityNotFoundException::new));
            mealRepository.save(mealEntity);
        }
    }
}
