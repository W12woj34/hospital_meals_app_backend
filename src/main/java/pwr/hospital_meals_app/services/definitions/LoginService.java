package pwr.hospital_meals_app.services.definitions;

import pwr.hospital_meals_app.dto.additionals.PasswordChangeDto;
import pwr.hospital_meals_app.dto.base.LoginDto;

import java.util.Optional;

public interface LoginService extends CrudService<LoginDto, Integer> {

    void changePassword(Integer id, PasswordChangeDto dto);

    Optional<Integer> getUserLoginId(String token);

    String refresh(String refreshToken);

    String encodePassword(String password);

    String getUserPassword(Integer id);
}
