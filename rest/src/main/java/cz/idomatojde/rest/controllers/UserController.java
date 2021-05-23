package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.dto.user.UserCreditsDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import cz.idomatojde.rest.controllers.base.AuthState;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.http.ResponseEntity.ok;

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

    private final OfferFacade offerFacade;

    @Inject
    public UserController(UserFacade users, OfferFacade offers) {
        super(users, users);
        offerFacade = offers;
    }

    @GetMapping("contactInfo/{userId}")
    ResponseEntity<UserContactInfoDTO> getUserContactInfo(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);
        if (!relatedOnlyPermission(auth, userId)) return forbidden(null);

        return ok(facade.getUserContactInfo(userId));
    }

    @GetMapping("credits/{userId}")
    ResponseEntity<UserCreditsDTO> getUserCredits(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);
        if (!adminOrOwnerOnlyPermission(auth, userId)) return forbidden(null);

        return ok(facade.getCredits(userId));
    }

    @PostMapping("setContactInfo/{userId}?phoneNum={phoneNum}")
    ResponseEntity<Void> changePhoneNumber(@RequestHeader(value = "token") String token, @PathVariable long userId, @PathVariable String phoneNum) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();
        if (!ownerOnlyPermission(auth.principalId(), userId)) return forbidden();

        facade.changePhoneNumber(userId, phoneNum);
        return ok().build();
    }

    @PostMapping("setCredits/{userId}?credits={credits}")
    ResponseEntity<Void> setUserCredits(@RequestHeader(value = "token") String token, @PathVariable long userId, @PathVariable int credits) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();
        // TODO only after valid purchase...

        facade.setCredits(userId, credits);
        return ok().build();
    }


    private boolean relatedOnlyPermission(AuthState state, long userId) {
        var subscribedBy = offerFacade.getAllSubscribedBy(state.principal());
        var ownedBy = offerFacade.getAllOwnedBy(facade.getById(userId));

        boolean match = false;
        for (OfferDTO s : subscribedBy) {
            for (OfferDTO o : ownedBy) {
                if (s == o) {
                    match = true;
                    break;
                }
            }
            if (match) {
                break;
            }
        }
        return match || adminOrOwnerOnlyPermission(state, userId);
    }

    private boolean adminOrOwnerOnlyPermission(AuthState state, long userId) {
        return state.admin() || ownerOnlyPermission(state.principalId(), userId);
    }

    private boolean ownerOnlyPermission(long principalId, long userId) {
        return principalId == userId;
    }
}
