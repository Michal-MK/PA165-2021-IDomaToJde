package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.rest.controllers.base.BaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Controller responsible for all things concerning Offers
 *
 * @author Michal Hazdra
 */
@Api(tags = "Offers Endpoint")
@RestController
@RequestMapping("offers")
public class OfferController extends BaseRESTController<OfferFacade, RegisterOfferDTO, OfferDTO> {
    @Inject
    public OfferController(OfferFacade offers) {
        super(offers);
    }

    @PostMapping("changeDescription")
    void changeDescription(ChangeDescriptionOfferDTO dto) {
        facade.changeDescription(dto);
    }
}
