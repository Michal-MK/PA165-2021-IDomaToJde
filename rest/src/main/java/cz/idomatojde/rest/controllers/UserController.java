package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.BaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Controller responsible for all things concerning Users
 *
 * @author Michal Hazdra
 */
@Api(tags = "Users Endpoint")
@RestController
@RequestMapping("users")
public class UserController extends BaseRESTController<UserFacade, RegisterUserDTO, UserDTO> {
    @Inject
    public UserController(UserFacade users) {
        super(users);
    }
}
