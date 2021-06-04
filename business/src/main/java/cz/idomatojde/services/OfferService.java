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
    List<Offer> getOffersSubscribedToBy(User user);

    /**
     * Finds all {@link User}s that are subscribed to an {@link Offer}
     *
     * @param offerId the {@link Offer} id
     * @return list of all subscribers
     */
    List<User> getAllSubscribersOf(long offerId);

    /**
     * Finds all offers of the specified {@link Category}
     *
     * @param category the {@link Category} to filter by
     * @return the list of all offer part of the provided {@link Category}
     */
    List<Offer> getOffersByCategory(Category category);

    /**
     * Finds all offers containing the specified name in a paged manner
     *
     * @param nameFilter the containing name
     * @param pageNum    the requested page
     * @param size       the requested page size
     * @return the list of all {@link Offer}s matching the criteria
     */
    List<Offer> getFiltered(String nameFilter, int pageNum, int size);

    /**
     * Adds an offer as to a users subscription list
     *
     * @param userId  the subscriber's id
     * @param offerId the id of the offer being subscribed to
     */
    void addSubscription(long offerId, long userId);
}
