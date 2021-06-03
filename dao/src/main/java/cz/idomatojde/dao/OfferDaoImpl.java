package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAOImpl;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

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
        return em.createQuery("select o from Offer o where o.owner = :userid", Offer.class)
                .setParameter("userid", user)
                .getResultList();
    }

    @Override
    public List<Offer> getSubscribedOffers(User user) {
        return em.createQuery("select o from Offer o where :user member of o.subscribers", Offer.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Offer> getActiveOffers() {
        return em.createQuery("select o from Offer o where o.expirationDate >= :today", Offer.class)
                .setParameter("today", LocalDate.now())
                .getResultList();
    }

    @Override
    public List<Offer> getAllByCategory(Category category) {
        return em.createQuery("select o from Offer o where o.category = :cat", Offer.class)
                .setParameter("cat", category)
                .getResultList();
    }

    @Override
    public List<Offer> getFiltered(String nameFilter, int pageNum, int size) {
        if (nameFilter == null || nameFilter.isBlank()) {
            return findPaged(pageNum, size);
        }
        return em.createQuery("select o from Offer o where lower(o.title) like %:nameFilter%", Offer.class)
                .setParameter("nameFilter", nameFilter.toLowerCase(Locale.ROOT))
                .setFirstResult((pageNum - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }
}
