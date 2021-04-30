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
    public void addPhone(String phoneNumber, Long userId) {
        userDao.addPhone(phoneNumber, userId);
    }

    @Override
    public void addCredits(Integer credits, Long userId) {
        userDao.addCredits(credits, userId);
    }
}
