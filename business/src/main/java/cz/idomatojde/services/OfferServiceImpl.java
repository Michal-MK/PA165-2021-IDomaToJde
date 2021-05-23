package cz.idomatojde.services;

import cz.idomatojde.dao.OfferDao;
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

    @Inject
    public OfferServiceImpl(OfferDao offers) {
        super(offers);
        this.offers = offers;
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
    public List<Offer> getOffersSubscribedTo(User user) {
        return offers.getSubscribedOffers(user);
    }

    @Override
    public List<Offer> getOffersByCategory(Category category) {
        return offers.getAllByCategory(category);
    }
}
