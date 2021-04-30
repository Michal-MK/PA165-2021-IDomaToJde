package cz.idomatojde.services;

import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Jiri Vrbka
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
}