package pwr.hospital_meals_app.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import pwr.hospital_meals_app.config.TestAuthorizationConfig;
import pwr.hospital_meals_app.dto.base.EmployeeDto;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestAuthorizationConfig.class)
@FlywayTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    HttpHeaders authHeaderPatientMovement;
    @Autowired
    HttpHeaders authHeaderWardDietitian;

    private final String mapping;

    public EmployeeControllerTest(@LocalServerPort int port) {
        this.mapping = "http://localhost:" + port + RestMappings.EMPLOYEE;
    }


    @FlywayTest
    @Test
    void shouldReturnPersonalEntity() {

        HttpEntity<?> requestEntity =
                new HttpEntity<>(authHeaderWardDietitian);

        ResponseEntity<EmployeeDto> responseEntity =
                restTemplate.exchange(mapping + RestMappings.PERSONAL, HttpMethod.GET,
                        requestEntity, EmployeeDto.class);

        EmployeeDto responseDto = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assert responseDto != null;
        assertEquals(2, responseDto.getId());
        assertEquals("46020203212", responseDto.getPerson().getPesel());
    }

    @FlywayTest
    @Test
    void shouldReturnAll() throws IOException {

        HttpEntity<?> requestEntity =
                new HttpEntity<>(authHeaderPatientMovement);

        ResponseEntity<String> responseEntity = restTemplate.exchange(mapping,
                HttpMethod.GET,
                requestEntity,
                String.class);

        JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
        ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<EmployeeDto>>() {
        });

        List<EmployeeDto> responseList = listReader.readValue(jsonResponseBody.get("content"));
        assertThat(responseList).hasSize(10);
    }

    @FlywayTest
    @Test
    void shouldReturn403NoAuth() {

        ResponseEntity<?> responseEntity =
                restTemplate.getForEntity(mapping, Object.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @FlywayTest
    @Test
    void shouldReturnEntityUnderExistingId() {
        HttpEntity<?> requestEntity =
                new HttpEntity<>(authHeaderPatientMovement);

        ResponseEntity<EmployeeDto> responseEntity =
                restTemplate.exchange(mapping + "/8", HttpMethod.GET,
                        requestEntity, EmployeeDto.class);

        EmployeeDto responseDto = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assert responseDto != null;
        assertEquals(8, responseDto.getId());
        assertEquals("46042214643", responseDto.getPerson().getPesel());

    }

    @FlywayTest
    @Test
    void shouldPutGivenEntity() {

        HttpEntity<?> initRequestEntity =
                new HttpEntity<>(authHeaderPatientMovement);

        ResponseEntity<EmployeeDto> initResponse =
                restTemplate.exchange(mapping + "/8", HttpMethod.GET, initRequestEntity, EmployeeDto.class);
        EmployeeDto existingDto = initResponse.getBody();

        HttpEntity<EmployeeDto> requestEntity =
                new HttpEntity<>(existingDto, authHeaderPatientMovement);

        ResponseEntity<EmployeeDto> responseEntity =
                restTemplate.exchange(mapping + "/8", HttpMethod.PUT, requestEntity, EmployeeDto.class);

        EmployeeDto updatedDto = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(8, Objects.requireNonNull(updatedDto).getId());
    }
}
