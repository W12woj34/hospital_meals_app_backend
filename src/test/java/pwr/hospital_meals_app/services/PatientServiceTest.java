package pwr.hospital_meals_app.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pwr.hospital_meals_app.dto.additionals.PatientDataDto;
import pwr.hospital_meals_app.persistance.entities.*;
import pwr.hospital_meals_app.persistance.repositories.PatientRepository;
import pwr.hospital_meals_app.services.implementations.PatientServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;
    @InjectMocks
    PatientServiceImpl patientService;

    @Test
    void shouldReturnProperPatientDataById() {

        PatientEntity patient = new PatientEntity();


        PersonEntity person = new PersonEntity();
        person.setPesel("11111111111");
        person.setFirstName("Jan");
        person.setLastName("Kowalski");
        person.setBirthDate(LocalDate.parse("1991-01-15"));
        person.setPatient(patient);

        WardEntity ward = new WardEntity();
        ward.setName("Chirurgia");

        StayEntity stay = new StayEntity();
        stay.setAdmissionDate(LocalDate.parse("2020-05-16"));
        stay.setArchived(false);
        stay.setPatient(patient);
        stay.setWard(ward);
        ward.setStays(Collections.singletonList(stay));

        patient.setAdditionalInfo("additional info test");
        patient.setPerson(person);
        patient.setStays(Collections.singletonList(stay));
        patient.setPatientDiets(new ArrayList<>());

        given(patientRepository.findById(16))
                .willReturn(java.util.Optional.of(patient));

        PatientDataDto dto = patientService.getPatientData(16);

        assertEquals("additional info test", dto.getAdditionalInfo());
        assertEquals(LocalDate.parse("1991-01-15"), dto.getBirthDate());
        assertEquals("Jan", dto.getFirstName());
        assertEquals("Kowalski", dto.getLastName());
        assertEquals("11111111111", dto.getPesel());
        assertEquals("", dto.getDiet());
        assertEquals("Chirurgia", dto.getWard());
        assertEquals(LocalDate.parse("2020-05-16"), dto.getAdmissionDate());
    }
}
