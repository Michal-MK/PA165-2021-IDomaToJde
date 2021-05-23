package cz.idomatojde.services;

import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseService;

import java.util.List;

/**
 * Service class for {@link Offer}
 *
 * @author Jiri Vrbka & Michal Hazdra
 */
public interface OfferService extends BaseService<Offer> {

    /**
     * Finds all offers owned by a user
     *
     * @param user Owner of offers
     * @return offers owned by given user
     */
    List<Offer> findByUser(User user);

    /**
     * Gets all offers that did not expired yet
     *
     * @return list of not expired offers
     */
    List<Offer> getActiveOffers();

    /**
     * Finds all offers the user is subscribed to
     *
     * @param user the user to filter by
     * @return the list of all offer this user subscribed to
     */
    List<Offer> getOffersSubscribedTo(User user);

    /**
     * Finds all offers of the specified {@link Category}
     *
     * @param category the {@link Category} to filter by
     * @return the list of all offer part of the provided {@link Category}
     */
    List<Offer> getOffersByCategory(Category category);
}
