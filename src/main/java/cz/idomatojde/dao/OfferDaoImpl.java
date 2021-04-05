package cz.idomatojde.dao;

import cz.idomatojde.entity.Offer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.sql.Date;

/*
Created by Jiri Vrbka
 */
@Repository
public class OfferDaoImpl implements OfferDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Offer offer) {
        em.persist(offer);
    }

    @Override
    public List<Offer> findAll() {
        TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o",
                Offer.class);
        return query.getResultList();
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
    public Offer findById(Long id) {
        return em.find(Offer.class, id);
    }

    @Override
    public void remove(Offer o) throws IllegalArgumentException {
        em.remove(o);
    }

    @Override
    public List<Offer> getActiveOffers() {

        TypedQuery<Offer> query = em
                .createQuery(
                        "SELECT o FROM Offer o WHERE o.expirationDate >= CURRENT_DATE",
                        Offer.class);
        return query.getResultList();
    }
}
