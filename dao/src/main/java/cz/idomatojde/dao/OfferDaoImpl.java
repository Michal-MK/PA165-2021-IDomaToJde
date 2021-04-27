package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAOImpl;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;
import cz.idomatojde.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(Offer offer) {
        em.merge(offer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Offer> findByUser(User user) {
        return em.createQuery(
                "Select o from Offer o where o.owner = :userid",
                Offer.class)
                .setParameter("userid", user)
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
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
