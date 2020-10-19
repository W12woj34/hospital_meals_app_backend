package pwr.hospital_meals_app.services.definitions;

import pwr.hospital_meals_app.dto.additionals.PasswordChangeDto;
import pwr.hospital_meals_app.dto.additionals.TokensDto;
import pwr.hospital_meals_app.dto.base.LoginDto;

import java.util.Optional;

public interface LoginService extends CrudService<LoginDto, Integer> {

    boolean changePassword(Integer id, PasswordChangeDto dto);

    Optional<Integer> getUserLoginId(String token);

    TokensDto refresh(String refreshToken);

    String encodePassword(String password);

    boolean changePasswordForce(Integer id, String newPassword);
}
