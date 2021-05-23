package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsible for all things concerning Offers
 *
 * @author Michal Hazdra
 */
@Api(tags = "Offers Endpoint")
@RestController
@RequestMapping("offers")
public class OfferController extends
        AuthBaseRESTController<OfferFacade, RegisterOfferDTO, OfferDTO> {

    @Inject
    public OfferController(UserFacade userFacade, OfferFacade offers) {
        super(userFacade, offers);
    }

    @PostMapping("changeDescription")
    ResponseEntity<Void> changeDescription(@RequestHeader(value = "token") String token, ChangeDescriptionOfferDTO dto) {
        if (notAuthenticated(token)) return unauthorized();

        facade.changeDescription(dto);
        return ok().build();
    }
}
