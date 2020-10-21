package pwr.hospital_meals_app.services.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.additionals.PatientDataDto;
import pwr.hospital_meals_app.dto.base.EmployeeDto;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.EmployeeEntity;
import pwr.hospital_meals_app.persistance.entities.PatientDietEntity;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.persistance.repositories.PatientRepository;
import pwr.hospital_meals_app.persistance.repositories.PersonRepository;
import pwr.hospital_meals_app.persistance.repositories.WardNurseRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.PatientService;
import pwr.hospital_meals_app.services.mappers.PatientMapper;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static pwr.hospital_meals_app.security.SecurityConstants.SECRET_AUTH;
import static pwr.hospital_meals_app.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class PatientServiceImpl
        extends BaseSpecificationCrudService<PatientDto, PatientEntity, Integer, PatientRepository>
        implements PatientService {


    private final LoginRepository loginRepository;
    private final WardNurseRepository wardNurseRepository;
    private final PersonRepository personRepository;

    public PatientServiceImpl(PatientRepository repository,
                              PatientMapper mapper,
                              LoginRepository loginRepository,
                              WardNurseRepository wardNurseRepository, PersonRepository personRepository) {
        super(repository, mapper);
        this.loginRepository = loginRepository;
        this.wardNurseRepository = wardNurseRepository;
        this.personRepository = personRepository;
    }

    @Override
    public PatientDto save(PatientDto dto) {
        PatientEntity entity = mapper.mapToEntity(dto);
        entity.setPerson(personRepository.findById(dto.getId())
                .orElseThrow(EntityNotFoundException::new));
        PatientEntity savedEntity = repository.save(entity);

        return mapper.mapToDto(savedEntity);
    }

    @Override
    public Optional<PatientDto> updateById(PatientDto dto, Integer id) {
        Optional<PatientEntity> entityOptional = repository.findById(id);

        return entityOptional.map(
                entity -> {
                    dto.setId(id);
                    PatientEntity employeeEntity = mapper.mapToEntity(dto);
                    employeeEntity.setPerson(personRepository.findById(dto.getId())
                            .orElseThrow(EntityNotFoundException::new));
                    repository.save(employeeEntity);
                    return mapper.mapToDto(entity);
                });
    }

    @Override
    public Page<PatientDataDto> getPatientsData(Pageable pageable) {

        List<PatientDataDto> dtos = new LinkedList<>();
        Page<PatientEntity> patients = repository.findAll(pageable);

        for (PatientEntity patient : patients) {
            PatientDataDto dto = createPatientDataDto(patient, null);
            if (dto != null) {
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos);
    }

    @Override
    public List<PatientDataDto> getPatientsData() {

        List<PatientDataDto> dtos = new LinkedList<>();
        List<PatientEntity> patients = repository.findAll();

        for (PatientEntity patient : patients) {
            PatientDataDto dto = createPatientDataDto(patient, null);
            if (dto != null) {
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public Page<PatientDataDto> getPatientsDataFromWard(String token) {

        Integer ward = resolveWardIdFromToken(token);
        List<PatientDataDto> dtos = new LinkedList<>();
        List<PatientEntity> patients = repository.findAll();

        for (PatientEntity patient : patients) {
            PatientDataDto dto = createPatientDataDto(patient, ward);
            if (dto != null) {
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos);
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


    @Override
    public PatientDataDto getPatientData(Integer id) {

        Optional<PatientEntity> patient = repository.findById(id);
        if (patient.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return createPatientDataDto(patient.get(), null);
    }

    private PatientDataDto createPatientDataDto(PatientEntity patient, Integer ward) {

        PatientDataDto dto = new PatientDataDto();

        List<PatientDietEntity> patientActualDiet = patient.getPatientDiets().stream()
                .filter(p -> p.getEndDate() == null).collect(Collectors.toList());


        if (patientActualDiet.isEmpty()) {
            dto.setDiet("");
        } else if (patientActualDiet.size() > 1) {
            return null;
        } else {
            dto.setDiet(patientActualDiet.get(0).getDiet().getName());
        }

        List<StayEntity> patientStay = patient.getStays().stream()
                .filter(s -> !s.isArchived()).collect(Collectors.toList());

        if (ward != null) {
            patientStay = patientStay.stream()
                    .filter(s -> Objects.equals(s.getWard().getId(), ward)).collect(Collectors.toList());
        }


        if (patientStay.isEmpty()) {
            return null;
        } else if (patientStay.size() > 1) {
            return null;
        } else {
            dto.setAdmissionDate(patientStay.get(0).getAdmissionDate());
            dto.setWard(patientStay.get(0).getWard().getName());
        }

        dto.setId(patient.getId());
        dto.setFirstName(patient.getPerson().getFirstName());
        dto.setLastName(patient.getPerson().getLastName());
        dto.setBirthDate(patient.getPerson().getBirthDate());
        dto.setPesel(patient.getPerson().getPesel());
        dto.setAdditionalInfo(patient.getAdditionalInfo());

        return dto;
    }
}
