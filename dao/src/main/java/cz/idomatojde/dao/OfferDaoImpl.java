package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAOImpl;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * DAO implementation for {@link OfferDao} API
 *
 * @author Jiri Vrbka
 */
@Repository
public class OfferDaoImpl extends BaseDAOImpl<Offer> implements OfferDao {

    public OfferDaoImpl() {
        super(Offer.class);
    }

    @Override
    public List<Offer> findByUser(User user) {
        return em.createQuery(
                "Select o from Offer o where o.owner = :userid",
                Offer.class)
                .setParameter("userid", user)
                .getResultList();
    }

    @Override
    public List<Offer> getSubscribedOffers(User user) {
        return em.createQuery("select o from Offer o where :user in o.subscribers", Offer.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Offer> getActiveOffers() {
        return em
                .createQuery(
                        "SELECT o FROM Offer o WHERE o.expirationDate >= :today",
                        Offer.class)
                .setParameter("today", LocalDate.now())
                .getResultList();
    }
}
