package cz.idomatojde.dao;

import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

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
        return em.createQuery("SELECT o FROM Offer o",
                Offer.class)
                .getResultList();
    }

    @Override
    public List<Offer> findByUser(User u) {
        return em.createQuery(
                "Select o from Offer o where o.owner = :userid",
                Offer.class)
                .setParameter("userid", u)
                .getResultList();
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
        return em
                .createQuery(
                        "SELECT o FROM Offer o WHERE o.expirationDate >= :today",
                        Offer.class)
                .setParameter("today", LocalDate.now())
                .getResultList();
    }
}
