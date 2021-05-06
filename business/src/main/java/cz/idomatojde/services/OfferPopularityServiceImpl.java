package cz.idomatojde.services;

import cz.idomatojde.dao.CategoryDao;
import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Michal Hazdra
 */
@Service
public class OfferPopularityServiceImpl implements OfferPopularityService {

    private final UserDao users;

    private final CategoryDao categories;

    private final OfferDao offers;

    @Inject
    public OfferPopularityServiceImpl(UserDao users, CategoryDao categories, OfferDao offers) {
        this.users = users;
        this.categories = categories;
        this.offers = offers;
    }

    @Override
    public List<Offer> getPopularOffersByPreferredCategory(User user) {
        Map<Category, List<Offer>> offersByCategory = offers.getSubscribedOffers(user).stream()
                .collect(Collectors.groupingBy(Offer::getCategory));

        Category mostPopular = null;
        int highest = 0;

        for (Category c : offersByCategory.keySet()) {
            int curSize = offersByCategory.get(c).size();
            if (curSize > highest) {
                mostPopular = c;
                highest = curSize;
            }
        }

        return getPopularOffersByPreferredCategory(mostPopular);
    }

    @Override
    public List<Offer> getPopularOffersByPreferredCategory(Category category) {
        List<Offer> activeOffers = offers.getActiveOffers();

        return activeOffers.stream()
                .filter(f -> f.getCategory() == category)
                .filter(f -> f.getCapacity() - f.getRegistered() > 0)
                .sorted(Comparator.comparingInt(Offer::getCapacity).reversed())
                .collect(Collectors.toList());
    }
}
