package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.dto.user.UserCreditsDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.facade.TimetableFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import cz.idomatojde.rest.controllers.base.AuthState;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsible for all things concerning Users
 *
 * @author Michal Hazdra
 */
@Api(tags = "Users Endpoint")
@RestController
@RequestMapping("api/users")
public class UserController extends
        AuthBaseRESTController<UserFacade, RegisterUserDTO, UserDTO> {

    private final OfferFacade offerFacade;
    private final TimetableFacade timetableFacade;

    @Inject
    public UserController(UserFacade users, OfferFacade offers, TimetableFacade timetables) {
        super(users, users, false, false, false, false);
        offerFacade = offers;
        timetableFacade = timetables;
    }

    @GetMapping("contactInfo/{userId}")
    ResponseEntity<UserContactInfoDTO> getUserContactInfo(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);
        if (!relatedOnlyPermission(auth, userId)) return forbidden(null);

        return ok(facade.getUserContactInfo(userId));
    }

    @PutMapping(value = "signup")
    ResponseEntity<Long> signup(@RequestBody RegisterUserDTO regDto) {
        try {
            Long userId = facade.register(regDto);

            var timetable = new AddTimetableDTO();
            timetable.setUserId(userId);
            timetable.setYear(LocalDate.now().getYear());
            timetable.setWeek(LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR));
            timetableFacade.register(timetable);

            return ok(userId);
        } catch (Exception e) {
            // TODO provide more info if possible
            return badRequest().body(-1L);
        }
    }

    @GetMapping("credits/{userId}")
    ResponseEntity<UserCreditsDTO> getUserCredits(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);
        if (!adminOrOwnerOnlyPermission(auth, userId)) return forbidden(null);

        return ok(facade.getCredits(userId));
    }

    @PostMapping("setContactInfo/{userId}")
    ResponseEntity<Void> changePhoneNumber(@RequestHeader(value = "token") String token, @PathVariable long userId, @RequestParam String phoneNum) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();
        if (!ownerOnlyPermission(auth.principalId(), userId)) return forbidden();

        facade.changePhoneNumber(userId, phoneNum);
        return ok().build();
    }

    @PostMapping("setCredits/{userId}/{credits}")
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

    @Override
    protected boolean isOwner(Long principalId, Long resourceId) {
        return false; // Users do not own their instance in the database, therefore cannot delete themselves
    }

    @Override
    protected boolean allowedToRegister(AuthState state, RegisterUserDTO registerUserDTO) {
        return true; // Anyone can register into the system
    }
}