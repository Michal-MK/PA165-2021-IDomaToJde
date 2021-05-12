package cz.idomatojde.services;

import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Michal Hazdra
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final UserDao userDao;

    @Inject
    public UserServiceImpl(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }

    @Override
    public void addPhone(long userId, String phoneNumber) {
        userDao.addPhone(userId, phoneNumber);
    }

    @Override
    public void addCredits(long userId, int credits) {
        userDao.addCredits(userId, credits);
    }
}
