package cz.idomatojde.facade;

import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;

/**
 * Facade responsible for all things concerning offers
 *
 * @author Jiri Vrbka
 */
public interface OfferFacade {

    /**
     * Register a new offer
     *
     * @param registerOfferDTO necessary information for offer creation
     */
    void registerOffer(RegisterOfferDTO registerOfferDTO);

    /**
     * Get an offer with given id
     *
     * @param id unique offer identifier
     * @return DTO with offer information
     */
    OfferDTO getOfferWithId(long id);

    /**
     * Removes offer
     *
     * @param id unique offer identifier
     */
    void removeOffer(long id);

    /**
     * Changes description and title of offer
     *
     * @param changeDescriptionOfferDTO DTO with necessary information
     */
    void changeDescription(ChangeDescriptionOfferDTO changeDescriptionOfferDTO);
}
