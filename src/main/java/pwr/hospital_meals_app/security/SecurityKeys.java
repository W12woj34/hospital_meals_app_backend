package pwr.hospital_meals_app.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SecurityKeys {


    @Value("${jwt-secret-key}")
    private String SECRET_AUTH;

    @Value("${refresh-token-secret-key}")
    private String SECRET_REFRESH;
}
