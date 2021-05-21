package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.dto.user.UserCreditsDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
public class UserController extends
        AuthBaseRESTController<UserFacade, RegisterUserDTO, UserDTO> {
    @Inject
    public UserController(UserFacade users) {
        super(users, users);
    }

    @GetMapping("contactInfo/{userId}")
    UserContactInfoDTO getUserContactInfo(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        return facade.getUserContactInfo(userId);
    }

    @GetMapping("credits/{userId}")
    UserCreditsDTO getUserCredits(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        return facade.getCredits(userId);
    }

    @PostMapping("setContactInfo/{userId}?phoneNum={phoneNum}")
    void changePhoneNumber(@RequestHeader(value = "token") String token, @PathVariable long userId, @PathVariable String phoneNum) {
        facade.changePhoneNumber(userId, phoneNum);
    }

    @PostMapping("setCredits/{userId}?credits={credits}")
    void setUserCredits(@RequestHeader(value = "token") String token, @PathVariable long userId, @PathVariable int credits) {
        facade.setCredits(userId, credits);
    }
}
