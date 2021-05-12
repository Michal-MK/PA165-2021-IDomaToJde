package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;

import java.util.List;

/**
 * API for {@link Offer} Entities
 *
 * @author Jiri Vrbka
 */
public interface OfferDao extends BaseDAO<Offer> {
    /**
     * Finds all offers owned by a user
     *
     * @param user Owner of offers
     * @return offers owned by given user
     */
    List<Offer> findByUser(User user);

    /**
     * Finds all offers a {@link User} is subscribed to
     *
     * @param user the {@link User}  to filter with
     * @return {@link Offer}s subscribed to by a given {@link User}
     */
    List<Offer> getSubscribedOffers(User user);

    /**
     * Gets all offers that did not expired yet
     *
     * @return list of not expired offers
     */
    List<Offer> getActiveOffers();

    /**
     * Gets all offers part of a specific category
     *
     * @param category the category name to filter by
     * @return list of all {@link Offer}s that are part of the category
     */
    List<Offer> getAllByCategory(Category category);
}
