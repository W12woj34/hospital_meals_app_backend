package pwr.hospital_meals_app.services.implementations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.additionals.PatientDataDto;
import pwr.hospital_meals_app.dto.base.PatientDto;
import pwr.hospital_meals_app.persistance.entities.PatientDietEntity;
import pwr.hospital_meals_app.persistance.entities.PatientEntity;
import pwr.hospital_meals_app.persistance.entities.StayEntity;
import pwr.hospital_meals_app.persistance.repositories.PatientRepository;
import pwr.hospital_meals_app.services.definitions.BaseSpecificationCrudService;
import pwr.hospital_meals_app.services.definitions.PatientService;
import pwr.hospital_meals_app.services.mappers.PatientMapper;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl
        extends BaseSpecificationCrudService<PatientDto, PatientEntity, Integer, PatientRepository>
        implements PatientService {

    public PatientServiceImpl(PatientRepository repository,
                              PatientMapper mapper) {
        super(repository, mapper);
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
    public List<PatientDataDto> getPatientsDataFromWard(Integer ward) {

        List<PatientDataDto> dtos = new LinkedList<>();
        List<PatientEntity> patients = repository.findAll();

        for (PatientEntity patient : patients) {
            PatientDataDto dto = createPatientDataDto(patient, ward);
            if (dto != null) {
                dtos.add(dto);
            }
        }
        return dtos;
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

        if (patientStay.isEmpty()) {
            return null;
        } else if (patientStay.size() > 1) {
            return null;
        } else {
            if (Objects.equals(patientStay.get(0).getWard().getId(), ward)) {
                dto.setWard(patientStay.get(0).getWard().getName());
            } else {
                return null;
            }

        }

        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setBirthDate(patient.getBirthDate());
        dto.setPesel(patient.getPesel());

        return dto;
    }
}
