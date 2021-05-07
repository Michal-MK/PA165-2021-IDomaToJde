package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.facade.UserFacade;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Controller responsible for all things concerning Users
 *
 * @author Michal Hazdra
 */
@Api(tags = "Users Endpoint")
@RestController("users")
public class UserController {

    final UserFacade users;

    @Inject
    public UserController(UserFacade users) {
        this.users = users;
    }

    @GetMapping("/get/{userId}")
    public UserDTO getAllUsers(@PathVariable long userId) {
        return users.getById(userId);
    }
}
