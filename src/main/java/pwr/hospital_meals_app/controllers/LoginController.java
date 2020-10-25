package pwr.hospital_meals_app.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.hospital_meals_app.dto.additionals.PasswordChangeDto;
import pwr.hospital_meals_app.dto.additionals.TokensDto;
import pwr.hospital_meals_app.dto.base.LoginDto;
import pwr.hospital_meals_app.services.definitions.LoginService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(RestMappings.LOGIN)
@Api(tags = "Logins")
public class LoginController
        extends BaseRestCrudController<
        LoginDto, Integer> {

    private final LoginService service;

    public LoginController(LoginService service) {
        super(service);
        this.service = service;

    }

    @ApiOperation(value = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping(RestMappings.SIGN_UP)
    public ResponseEntity<LoginDto> signUp(@RequestHeader("Authorization") String token,
                                           @Valid @RequestBody LoginDto dto, HttpServletRequest request) {
        dto.setPassword(service.encodePassword(dto.getPassword()));
        return create(token, dto, request);
    }

    @ApiOperation(value = "Refresh jwt token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping(RestMappings.REFRESH_TOKEN)
    public TokensDto refresh(@RequestParam String refreshToken) {
        return service.refresh(refreshToken);
    }


    @ApiOperation(value = "", hidden = true)
    @PutMapping(RestMappings.CHANGE_PASSWORD + RestMappings.ID)
    public boolean changePassword(
            @PathVariable Integer id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        @Valid PasswordChangeDto dto = new PasswordChangeDto(oldPassword, newPassword);

        return service.changePassword(id, dto);

    }

    @ApiOperation(value = "Change user password by id from token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PutMapping(RestMappings.CHANGE_PASSWORD)
    public boolean changeYourPassword(@RequestHeader("Authorization") String token,
                                      @RequestParam String oldPassword,
                                      @RequestParam String newPassword) {

        Integer id = service.getUserLoginId(token).orElseThrow(EntityNotFoundException::new);
        return changePassword(id, oldPassword, newPassword);
    }

    @ApiOperation(value = "Force change user password by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PutMapping(RestMappings.CHANGE_PASSWORD_FORCE + RestMappings.ID)
    public boolean changePasswordForce(
            @PathVariable Integer id,
            @RequestParam String newPassword) {

        return service.changePasswordForce(id, newPassword);

    }

    @ApiOperation(value = "Check if user with given username exist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorised"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(RestMappings.EXIST_USERNAME + RestMappings.USERNAME)
    public boolean existByUsername(@PathVariable String username) {
        return service.existsByUsername(username);
    }
}
