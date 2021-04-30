package cz.idomatojde.services;

import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseService;

import java.util.List;

/**
 * Service class for {@link Offer}
 *
 * @author Jiri Vrbka
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
}
