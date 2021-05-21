package cz.idomatojde.services;

import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseServiceImpl;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;

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

    @Override
    public boolean authenticate(String username, String pass) {
        User u = userDao.getByUsername(username);
        if (u == null) {
            return false;
        }

        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        return encoder.matches(pass, u.getPassword());
    }

    @Override
    public User authenticate(String token) {
        return userDao.getByToken(token);
    }

    @Override
    public void saveToken(String username, String token) {
        User u = userDao.getByUsername(username);
        if (u == null) {
            return;
        }

        u.setToken(token);
        u.setTokenExpiration(LocalDate.now().plusDays(1));
    }
}
