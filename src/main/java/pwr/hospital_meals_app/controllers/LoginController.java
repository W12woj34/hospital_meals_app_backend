package pwr.hospital_meals_app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.hospital_meals_app.dto.additionals.PasswordChangeDto;
import pwr.hospital_meals_app.dto.base.LoginDto;
import pwr.hospital_meals_app.services.definitions.LoginService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static pwr.hospital_meals_app.security.SecurityConstants.*;

@RestController
@RequestMapping(RestMappings.LOGIN)
public class LoginController
        extends BaseRestCrudController<
        LoginDto, Integer> {

    private final LoginService service;

    public LoginController(LoginService service) {
        super(service);
        this.service = service;

    }

    @PostMapping(RestMappings.SIGN_UP)
    public ResponseEntity<LoginDto> signUp(@RequestHeader("Authorization") String token,
                                           @Valid @RequestBody LoginDto dto, HttpServletRequest request) {
        dto.setPassword(service.encodePassword(dto.getPassword()));
        return create(token, dto, request);
    }

    @PostMapping(RestMappings.REFRESH_TOKEN)
    public void refresh(@RequestHeader("Refresh") String refreshToken, HttpServletResponse response) {
        response.addHeader(HEADER_STRING_AUTH, TOKEN_PREFIX + service.refresh(refreshToken));
    }

    @PutMapping(RestMappings.CHANGE_PASSWORD + RestMappings.ID)
    public void changePassword(
            @PathVariable Integer id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        @Valid PasswordChangeDto dto = new PasswordChangeDto(oldPassword, newPassword);

        service.changePassword(id, dto);

    }

    @PutMapping(RestMappings.CHANGE_PASSWORD)
    public void changeYourPassword(@RequestHeader("Authorization") String token,
                                   @RequestParam String oldPassword,
                                   @RequestParam String newPassword) {

        Integer id = service.getUserLoginId(token).orElseThrow(EntityNotFoundException::new);
        changePassword(id, oldPassword, newPassword);
    }

    @PutMapping(RestMappings.CHANGE_PASSWORD_FORCE + RestMappings.ID)
    public void changePasswordForce(
            @PathVariable Integer id,
            @RequestParam String newPassword) {

        @Valid PasswordChangeDto dto = new PasswordChangeDto(service.getUserPassword(id), newPassword);

        service.changePassword(id, dto);

    }
}
