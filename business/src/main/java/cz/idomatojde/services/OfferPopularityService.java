package cz.idomatojde.services;


import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;

import java.util.List;

/**
 * Service class for popular offers/courses filtering, based on {@link User}-{@link User} similarities
 *
 * @author Michal Hazdra
 */
public interface OfferPopularityService {
    /**
     * Function to obtain a list of offers a {@link User} may be interested in
     * based on users with similar characteristics
     *
     * @param user the user to analyze and "recommend" {@link Offer}s to
     * @return a list of interesting {@link Offer}s
     */
    List<Offer> getPopularOffersByPreferredCategory(User user);

    /**
     * Function to obtain a list of offers a {@link User} may be interested in
     * based on users with similar characteristics
     *
     * @param category the category to analyze and select {@link Offer}s from
     * @return a list of interesting {@link Offer}s
     */
    List<Offer> getPopularOffersByPreferredCategory(Category category);
}
