package pwr.hospital_meals_app.services.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.additionals.EmployeeDataDto;
import pwr.hospital_meals_app.dto.base.EmployeeDto;
import pwr.hospital_meals_app.persistance.entities.*;
import pwr.hospital_meals_app.persistance.repositories.*;
import pwr.hospital_meals_app.services.definitions.BasePersonalCrudService;
import pwr.hospital_meals_app.services.definitions.EmployeeService;
import pwr.hospital_meals_app.services.mappers.EmployeeMapper;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static pwr.hospital_meals_app.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class EmployeeServiceImpl
        extends BasePersonalCrudService<EmployeeDto, EmployeeEntity, EmployeeRepository>
        implements EmployeeService {

    private final MainKitchenDietitianRepository mainKitchenDietitianRepository;
    private final WardNurseRepository wardNurseRepository;
    private final DietitianRepository dietitianRepository;
    private final PatientMovementRepository patientMovementRepository;
    private final LoginRepository loginRepository;
    private final PersonRepository personRepository;

    @Value("${jwt-secret-key}")
    private String SECRET_AUTH;

    public EmployeeServiceImpl(EmployeeRepository repository,
                               EmployeeMapper mapper,
                               LoginRepository loginRepository,
                               MainKitchenDietitianRepository mainKitchenDietitianRepository,
                               WardNurseRepository wardNurseRepository,
                               DietitianRepository dietitianRepository,
                               PatientMovementRepository patientMovementRepository,
                               PersonRepository personRepository) {
        super(repository, mapper, loginRepository);
        this.mainKitchenDietitianRepository = mainKitchenDietitianRepository;
        this.wardNurseRepository = wardNurseRepository;
        this.dietitianRepository = dietitianRepository;
        this.patientMovementRepository = patientMovementRepository;
        this.loginRepository = loginRepository;
        this.personRepository = personRepository;
    }

    @Override
    public EmployeeDto save(EmployeeDto dto) {
        EmployeeEntity entity = mapper.mapToEntity(dto);
        entity.setPerson(personRepository.findById(dto.getId())
                .orElseThrow(EntityNotFoundException::new));
        EmployeeEntity savedEntity = repository.save(entity);

        return mapper.mapToDto(savedEntity);
    }

    @Override
    public Optional<EmployeeDto> updateById(EmployeeDto dto, Integer id) {
        Optional<EmployeeEntity> entityOptional = repository.findById(id);

        return entityOptional.map(
                entity -> {
                    dto.setId(id);
                    EmployeeEntity employeeEntity = mapper.mapToEntity(dto);
                    employeeEntity.setPerson(personRepository.findById(dto.getId())
                            .orElseThrow(EntityNotFoundException::new));
                    repository.save(employeeEntity);
                    return mapper.mapToDto(entity);
                });
    }


    @Override
    public Page<EmployeeDataDto> getEmployeesData(Pageable pageable) {

        List<EmployeeDataDto> dtos = new LinkedList<>();
        Page<EmployeeEntity> employees = repository.findAll(pageable);

        for (EmployeeEntity employee : employees) {
            dtos.add(createEmployeeDataDto(employee));
        }

        return new PageImpl<>(dtos);
    }

    @Override
    public List<EmployeeDataDto> getEmployeesData() {

        List<EmployeeDataDto> dtos = new LinkedList<>();
        List<EmployeeEntity> employees = repository.findAll();

        for (EmployeeEntity employee : employees) {
            dtos.add(createEmployeeDataDto(employee));
        }

        return dtos;
    }

    @Override
    public EmployeeDataDto getEmployeeData(Integer id) {

        Optional<EmployeeEntity> employee = repository.findById(id);
        if (employee.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return createEmployeeDataDto(employee.get());

    }

    @Override
    public EmployeeDataDto getEmployeeDataPersonal(String token) {
        return getEmployeeData(resolveIdFromToken(token));
    }

    private EmployeeDataDto createEmployeeDataDto(EmployeeEntity employee) {

        EmployeeDataDto dto = new EmployeeDataDto();

        dto.setId(employee.getId());
        dto.setFirstName(employee.getPerson().getFirstName());
        dto.setLastName(employee.getPerson().getLastName());
        dto.setBirthDate(employee.getPerson().getBirthDate());
        dto.setPesel(employee.getPerson().getPesel());

        Optional<MainKitchenDietitianEntity> kitchen = mainKitchenDietitianRepository
                .findById(Objects.requireNonNull(employee.getId()));
        if (kitchen.isPresent()) {
            dto.setRole("Dietetyk Kuchni");
            return dto;
        }

        Optional<WardNurseEntity> nurse = wardNurseRepository.findById(employee.getId());
        if (nurse.isPresent()) {
            dto.setRole("Pielęgniarka Oddziałowa");
            dto.setWard(nurse.get().getWard().getName());
            return dto;
        }

        Optional<DietitianEntity> dietitian = dietitianRepository.findById(employee.getId());
        if (dietitian.isPresent()) {
            dto.setRole("Dietetyk Oddziałowy");
            return dto;
        }

        Optional<PatientMovementEntity> movement = patientMovementRepository.findById(employee.getId());
        if (movement.isPresent()) {
            dto.setRole("Ruch Chorych");
            return dto;
        }

        throw new EntityNotFoundException();
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
}
