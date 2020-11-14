package pwr.hospital_meals_app.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import pwr.hospital_meals_app.persistance.repositories.LoginRepository;
import pwr.hospital_meals_app.security.SecurityConstants;
import pwr.hospital_meals_app.services.implementations.UserDetailsServiceImpl;

import java.util.Date;

import static pwr.hospital_meals_app.security.SecurityConstants.EXPIRATION_TIME;

@TestConfiguration
public class TestAuthorizationConfig {

    @Value("${jwt-secret-key}")
    private String SECRET_AUTH;

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final LoginRepository loginRepository;

    TestAuthorizationConfig(UserDetailsServiceImpl userDetailsServiceImpl,
                            LoginRepository loginRepository) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.loginRepository = loginRepository;
    }

    @Bean
    @Scope("prototype")
    HttpHeaders authHeaderWardNurse() {
        return makeJwtHeader(5);
    }

    @Bean
    @Scope("prototype")
    HttpHeaders authHeaderKitchenDietitian() {
        return makeJwtHeader(1);
    }

    @Bean
    @Scope("prototype")
    HttpHeaders authHeaderWardDietitian() {
        return makeJwtHeader(2);
    }

    @Bean
    @Scope("prototype")
    HttpHeaders authHeaderPatientMovement() {
        return makeJwtHeader(4);
    }

    private HttpHeaders makeJwtHeader(int credentialsId) {
        UserDetails userDetails = userDetailsServiceImpl
                .loadUserByUsername(loginRepository
                        .findById(credentialsId).orElseThrow().getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, SecurityConstants.TOKEN_PREFIX + generateJwt(userDetails));
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private String generateJwt(UserDetails userDetails) {

        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                        .setSubject(userDetails.getUsername())
                        .claim("role", userDetails.getAuthorities().stream().findFirst().orElseThrow().getAuthority())
                        .setIssuedAt(new Date(currentTimeMillis))
                        .setExpiration(new Date(currentTimeMillis + EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, SECRET_AUTH.getBytes())
                        .compact();

    }
}
