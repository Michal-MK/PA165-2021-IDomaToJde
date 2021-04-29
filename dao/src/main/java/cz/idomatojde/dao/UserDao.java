package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.User;

/**
 * API for {@link User} Entities
 *
 * @author Jiri Vrbka
 */
public interface UserDao extends BaseDAO<User> {
    /**
     * Adds a phone number to a user
     *
     * @param phoneNumber number to be added
     * @param userId      user id to be added number into
     */
    void addPhone(String phoneNumber, Long userId);

    /**
     * Adds credits to a user
     *
     * @param credits Number of credits to be added
     * @param userId  user id to be added credits to
     */
    void addCredits(Integer credits, Long userId);

    /**
     * Gets a user by email fields
     *
     * @param email email of user
     * @return user if user found, null otherwise
     */
    User getByEmail(String email);
}
