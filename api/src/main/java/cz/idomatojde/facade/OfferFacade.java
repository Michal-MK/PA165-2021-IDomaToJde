package cz.idomatojde.facade;

import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.facade.base.BaseFacade;

/**
 * Facade responsible for all things concerning offers
 *
 * @author Jiri Vrbka
 */
public interface OfferFacade extends BaseFacade<RegisterOfferDTO, OfferDTO> {

    /**
     * Changes description and title of offer
     *
     * @param changeDescriptionOfferDTO DTO with necessary information
     */
    void changeDescription(ChangeDescriptionOfferDTO changeDescriptionOfferDTO);
}
