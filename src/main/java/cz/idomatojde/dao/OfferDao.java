package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;

import java.util.List;

/*
 * @author Jiri Vrbka
 */
public interface OfferDao extends BaseDAO<Offer> {
    public void create(Offer offer);
    public List<Offer> findAll();
    public List<Offer> findByUser(User u);
    public List<Offer> getActiveOffers();
}
