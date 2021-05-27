package cz.idomatojde.facade;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.facade.base.BaseFacade;

import java.util.List;

/**
 * Facade responsible for all things concerning Offers
 *
 * @author Michal Hazdra
 */
public interface OfferFacade extends BaseFacade<RegisterOfferDTO, OfferDTO> {

    /**
     * Changes description and title of offer
     *
     * @param changeDescriptionOfferDTO DTO with necessary information
     */
    void changeDescription(ChangeDescriptionOfferDTO changeDescriptionOfferDTO);


    /**
     * Returns all {@link OfferDTO}s that the user is subscribed to
     *
     * @param user the user to filter by
     */
    List<OfferDTO> getAllSubscribedBy(UserDTO user);


    /**
     * Returns all {@link OfferDTO}s that the user is an author of
     *
     * @param user the user to filter by
     */
    List<OfferDTO> getAllOwnedBy(UserDTO user);


    /**
     * Returns all {@link OfferDTO}s that belong to the supplied category
     *
     * @param category the category to filter by
     */
    List<OfferDTO> getAllByCategory(CategoryDTO category);

}
