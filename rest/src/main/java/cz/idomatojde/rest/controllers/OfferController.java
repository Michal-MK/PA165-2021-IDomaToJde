package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsible for all things concerning Offers
 *
 * @author Michal Hazdra
 */
@Api(tags = "Offers Endpoint")
@RestController
@RequestMapping("api/offers")
public class OfferController extends
        AuthBaseRESTController<OfferFacade, RegisterOfferDTO, OfferDTO> {

    @Inject
    public OfferController(UserFacade userFacade, OfferFacade offers) {
        super(userFacade, offers, true,true, true, false);
    }

    @GetMapping("all")
    ResponseEntity<List<OfferDTO>> getAllOffers() {
        return ok(facade.getAll());
    }

    @GetMapping("ofUser/{userId}")
    ResponseEntity<List<OfferDTO>> ofUser(@PathVariable long userId) {
        var user = userFacade.getById(userId);
        var owned = facade.getAllOwnedBy(user);
        return ok(owned);
    }

    @GetMapping("withSubscribedUser/{userId}")
    ResponseEntity<List<OfferDTO>> withSubscribedUser(@PathVariable long userId) {
        var user = userFacade.getById(userId);
        var subscribed = facade.getAllSubscribedBy(user);
        return ok(subscribed);
    }

//    @GetMapping("getSubscribedUsers/{offerId}")
//    ResponseEntity<List<UserDTO>> getSubscribedUsers(@PathVariable long offerId) {
//        var user = userFacade.getById(userId);
//        var subscribed = facade.getAllSubscribedBy(user);
//        return ok(subscribed);
//    } TODO

    @PostMapping("changeDescription")
    ResponseEntity<Void> changeDescription(@RequestHeader(value = "token") String token, @RequestBody ChangeDescriptionOfferDTO dto) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();
        if (!ownerOnlyPermission(auth.principalId(), dto.getId())) return forbidden();

        facade.changeDescription(dto);
        return ok().build();
    }

    private boolean ownerOnlyPermission(long userId, long offerId) {
        OfferDTO dto = facade.getById(offerId);
        return dto.getOwner().getId() == userId;
    }

    @Override
    protected boolean isOwner(Long principalId, Long resourceId) {
        return facade.getById(resourceId).getOwner().getId().equals(principalId);
    }

    @Override
    protected boolean allowedToRegister(AuthState state, RegisterOfferDTO registerOfferDTO) {
        return state.authenticated();
    }
}
