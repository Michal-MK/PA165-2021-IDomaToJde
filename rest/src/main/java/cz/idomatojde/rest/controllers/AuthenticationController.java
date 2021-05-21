package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.AuthDTO;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.TokenGenerator;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Controller responsible for all things concerning Authentication
 *
 * @author Michal Hazdra
 */
@Api(tags = "Authentication Endpoint")
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final UserFacade users;

    @Inject
    public AuthenticationController(UserFacade users) {
        this.users = users;
    }

    @PostMapping("login")
    AuthDTO login(@RequestHeader(value = "username") String username, @RequestHeader(value = "pass")String pass) {
        AuthDTO response = users.authenticate(username, pass);

        if (response.isSuccessful()) {
            response.setToken(TokenGenerator.generateNewToken());
            users.saveToken(username, response.getToken());
        }
        return response;
    }
}
