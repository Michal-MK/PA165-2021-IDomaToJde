package cz.idomatojde.dao;

import cz.idomatojde.dao.Utils.UserContactInfo;
import cz.idomatojde.dao.common.BaseDAOImpl;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class UserDaoImpl extends BaseDAOImpl<User> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public void addPhone(String phoneNumber, Long userId) {
        var user = this.getById(userId);
        user.setPhoneNumber(phoneNumber);
        em.persist(user);
    }

    @Override
    public void addCredits(Integer credits, Long userId) {
        var user = this.getById(userId);
        user.setCredits(user.getCredits() + credits);
        em.persist(user);
    }

    @Override
    public UserContactInfo getContactInfo(Long userId) {
        var user = this.getById(userId);
        var info = new UserContactInfo();
        info.email = user.getEmail();
        info.name = user.getName();
        info.phone = user.getPhoneNumber();
        info.surname = user.getSurname();

        return info;
    }

    @Override
    public void update(User user) {
        // TODO do update
    }
}
