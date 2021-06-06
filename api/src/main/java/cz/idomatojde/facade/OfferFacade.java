package cz.idomatojde.facade;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.dto.user.UserDTO;
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
     * Returns all {@link OfferDTO}s containing provided name in a paged manner
     *
     * @param nameFilter the name to filter by
     * @param pageNum    the requested page
     * @param size       the requested page size
     */
    List<OfferDTO> getFiltered(String nameFilter, int pageNum, int size);

    /**
     * Returns all {@link OfferDTO}s that the user is subscribed to
     *
     * @param user the user to filter by
     */
    List<OfferDTO> getAllSubscribedBy(UserDTO user);

    /**
     * Returns all {@link UserDTO}s that are subscribed to this offer
     *
     * @param offerId the identifier of an offer
     * @return the list of all subscribers
     */
    List<UserDTO> getAllSubscribersOf(long offerId);


    /**
     * Adds an offer as to a users subscription list
     *
     * @param userId  the subscriber's id
     * @param offerId the id of the offer being subscribed to
     */
    void addSubscription(long userId, long offerId);


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
