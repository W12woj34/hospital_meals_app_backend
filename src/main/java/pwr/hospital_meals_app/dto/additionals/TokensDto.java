package pwr.hospital_meals_app.dto.additionals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokensDto {

    private String jwt;
    private String refreshToken;
}
