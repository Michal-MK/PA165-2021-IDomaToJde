package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAOImpl;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.time.LocalDate;

/**
 * DAO implementation for {@link OfferDao} API
 *
 * @author Jiri Vrbka
 */
@Repository
public class UserDaoImpl extends BaseDAOImpl<User> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public void addPhone(long userId, String phoneNumber) {
        var user = this.getById(userId);
        user.setPhoneNumber(phoneNumber);
    }

    @Override
    public void addCredits(long userId, int credits) {
        var user = this.getById(userId);
        user.setCredits(user.getCredits() + credits);
    }

    @Override
    public User getByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null e-mail"); //TODO Assert this?
        }
        try {
            return em.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getByUsername(String username) {
        if (username.isBlank()) {
            return null;
        }
        try {
            return em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getByToken(String token) {
        if (token.isBlank()) {
            return null;
        }
        try {
            User u = em.createQuery("select u from User u where u.token = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();

            if (u != null && u.getTokenExpiration().compareTo(LocalDate.now()) >= 0) {
                return u;
            }
            return null;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
