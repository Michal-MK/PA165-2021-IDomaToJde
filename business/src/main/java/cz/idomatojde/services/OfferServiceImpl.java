package cz.idomatojde.services;

import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Jiri Vrbka & Michal Hazdra
 */
@Service
public class OfferServiceImpl extends BaseServiceImpl<Offer> implements OfferService {

    private final OfferDao offers;
    private final UserDao users;

    @Inject
    public OfferServiceImpl(OfferDao offers, UserDao users) {
        super(offers);
        this.offers = offers;
        this.users = users;
    }

    @Override
    public List<Offer> findByUser(User user) {
        return offers.findByUser(user);
    }

    @Override
    public List<Offer> getActiveOffers() {
        return offers.getActiveOffers();
    }

    @Override
    public List<Offer> getOffersSubscribedToBy(User user) {
        return offers.getSubscribedOffers(user);
    }

    @Override
    public List<User> getAllSubscribersOf(long offerId) {
        return offers.getById(offerId).getSubscribers();
    }

    @Override
    public List<Offer> getOffersByCategory(Category category) {
        return offers.getAllByCategory(category);
    }

    @Override
    public List<Offer> getFiltered(String nameFilter, int pageNum, int size) {
        return offers.getFiltered(nameFilter, pageNum, size);
    }

    @Override
    public void addSubscription(long offerId, long userId) {
        var user = users.getById(userId);
        var offer = offers.getById(offerId);

        user.getSubscribedOffers().add(offer);
        offer.getSubscribers().add(user);
    }
}
