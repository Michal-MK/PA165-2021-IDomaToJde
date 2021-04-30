package cz.idomatojde.services;

import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl extends BaseServiceImpl<Offer> implements OfferService {

    private final OfferDao offers;

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