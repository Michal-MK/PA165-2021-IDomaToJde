package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.User;

public interface UserDao extends BaseDAO<User> {
    void addPhone(String phoneNumber, Long userId);
    void addCredits(Integer credits, Long userId);
}
