package pwr.hospital_meals_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.hospital_meals_app.dto.base.LoginDto;
import pwr.hospital_meals_app.services.definitions.LoginService;

@RestController
@RequestMapping(RestMappings.LOGIN)
public class LoginController
        extends BaseRestCrudController<
        LoginDto, Integer> {

    public LoginController(LoginService service) {
        super(service);
    }
}