package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.category.CategoryDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
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
        super(userFacade, offers, true, true, true, false);
    }

    @GetMapping("/all")
    ResponseEntity<List<OfferDTO>> getAllOffers() {
        return ok(facade.getAll());
    }

    @GetMapping("")
    public ResponseEntity<List<OfferDTO>> getPaged(@RequestParam Integer pageNum, @RequestParam Integer size,
                                                   @RequestParam(required = false) String nameFilter) {
        return ok(facade.getFiltered(nameFilter, pageNum, size));
    }

    @GetMapping("ofUser/{userId}")
    ResponseEntity<List<OfferDTO>> ofUser(@PathVariable long userId) {
        // TODO permission check ?

        var user = userFacade.getById(userId);
        return ok(facade.getAllOwnedBy(user));
    }

    @GetMapping("subscribedBy/{userId}")
    ResponseEntity<List<OfferDTO>> subscribedBy(@PathVariable long userId) {
        // TODO permission check ?

        var user = userFacade.getById(userId);
        return ok(facade.getAllSubscribedBy(user));
    }

    @GetMapping("subscribersOf/{offerId}")
    ResponseEntity<List<UserDTO>> subscribersOf(@PathVariable long offerId) {
        // TODO permission check ?

        return ok(facade.getAllSubscribersOf(offerId));
    }

    @PostMapping("changeDescription")
    ResponseEntity<Void> changeDescription(@RequestHeader(value = "token") String token, @RequestBody ChangeDescriptionOfferDTO dto) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();
        if (!ownerOnlyPermission(auth, dto.getId())) return forbidden();

        facade.changeDescription(dto);
        return ok().build();
    }

    private boolean ownerOnlyPermission(AuthState principal, long offerId) {
        OfferDTO dto = facade.getById(offerId);
        return dto.getOwner().getId() == principal.principalId() || principal.admin();
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
