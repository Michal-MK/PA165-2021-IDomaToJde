package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;

import java.util.List;

/*
 * @author Jiri Vrbka
 */
public interface OfferDao extends BaseDAO<Offer> {
    List<Offer> findByUser(User u);
    List<Offer> getActiveOffers();
}
