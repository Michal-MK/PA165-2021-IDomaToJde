package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAOImpl;
import cz.idomatojde.entity.Offer;
import org.springframework.stereotype.Repository;
import cz.idomatojde.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.sql.Date;

/*
Created by Jiri Vrbka
 */
@Repository
public class OfferDaoImpl extends BaseDAOImpl<Offer> implements OfferDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * Constructs the base type with the necessary information
     */
    public OfferDaoImpl() {
        super(Offer.class);
    }

    @Override
    public void update(Offer offer) {

    }

    @Override
    public List<Offer> findByUser(User u) {
        TypedQuery<Offer> query = em.createQuery(
                "Select o from Offer o where o.owner = :userid",
                Offer.class);

        query.setParameter("userid", u);
        return query.getResultList();
    }
    
    @Override
    public List<Offer> getActiveOffers() {

        TypedQuery<Offer> query = em
                .createQuery(
                        "SELECT o FROM Offer o WHERE o.expirationDate >= :date",
                        Offer.class).setParameter("date", LocalDateTime.now());
        return query.getResultList();
    }
}
