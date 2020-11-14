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
import org.springframework.web.util.UriComponentsBuilder;
import pwr.hospital_meals_app.config.TestAuthorizationConfig;
import pwr.hospital_meals_app.dto.base.OrderDto;

import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Import(TestAuthorizationConfig.class)
@FlywayTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    HttpHeaders authHeaderWardNurse;
    @Autowired
    HttpHeaders authHeaderWardDietitian;

    private final String mapping;

    public OrderControllerTest(@LocalServerPort int port) {
        this.mapping = "http://localhost:" + port + RestMappings.ORDER;
    }


    @FlywayTest
    @Test
    void shouldReturnAllWithSpecificPatientId() throws IOException {
        URI requestUri =
                UriComponentsBuilder.fromHttpUrl(mapping + "/search")
                        .queryParam("patientId", "11")
                        .build()
                        .encode()
                        .toUri();


        HttpEntity<?> requestEntity =
                new HttpEntity<>(authHeaderWardNurse);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUri,
                HttpMethod.GET,
                requestEntity,
                String.class);

        JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
        ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<OrderDto>>() {
        });

        List<OrderDto> responseList = listReader.readValue(jsonResponseBody.get("content"));
        assertThat(responseList).hasSize(3);
    }

    @FlywayTest
    @Test
    void shouldReturnAll() throws IOException {

        HttpEntity<?> requestEntity =
                new HttpEntity<>(authHeaderWardNurse);

        ResponseEntity<String> responseEntity = restTemplate.exchange(mapping,
                HttpMethod.GET,
                requestEntity,
                String.class);

        JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
        ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<OrderDto>>() {
        });

        List<OrderDto> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

        assertThat(responseAdList).hasSize(20);
    }


    @FlywayTest
    @Test
    void shouldReturn403WrongAuth() {

        HttpEntity<?> requestEntity =
                new HttpEntity<>(authHeaderWardDietitian);

        ResponseEntity<?> responseEntity =
                restTemplate.exchange(mapping, HttpMethod.GET, requestEntity, Object.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
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
                new HttpEntity<>(authHeaderWardNurse);

        ResponseEntity<OrderDto> responseEntity =
                restTemplate.exchange(mapping + "/9", HttpMethod.GET,
                        requestEntity, OrderDto.class);

        OrderDto responseDto = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assert responseDto != null;
        assertEquals(9, responseDto.getId());
        assertEquals("2020-08-14 22:46:42", responseDto.getTimestamp());

    }

    @FlywayTest
    @Test
    void shouldNotReturnEntityUnderNonExistingId() {
        HttpEntity<?> requestEntity =
                new HttpEntity<>(authHeaderWardNurse);

        assertThrows(RuntimeException.class, () ->
                restTemplate.exchange(mapping + "/999", HttpMethod.GET,
                        requestEntity, OrderDto.class));
    }

    @FlywayTest
    @Test
    void shouldPutGivenEntity() {

        HttpEntity<?> initRequestEntity =
                new HttpEntity<>(authHeaderWardNurse);

        ResponseEntity<OrderDto> initResponse =
                restTemplate.exchange(mapping + "/6", HttpMethod.GET, initRequestEntity, OrderDto.class);
        OrderDto existingDto = initResponse.getBody();

        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        Objects.requireNonNull(existingDto).setTimestamp(timestamp);

        HttpEntity<OrderDto> requestEntity =
                new HttpEntity<>(existingDto, authHeaderWardNurse);

        ResponseEntity<OrderDto> responseEntity =
                restTemplate.exchange(mapping + "/6", HttpMethod.PUT, requestEntity, OrderDto.class);

        OrderDto updatedDto = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(timestamp, Objects.requireNonNull(updatedDto).getTimestamp());
        assertEquals(6, updatedDto.getId());
    }
}
