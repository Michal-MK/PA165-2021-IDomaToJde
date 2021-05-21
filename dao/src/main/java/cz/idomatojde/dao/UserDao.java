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
    void addPhone(long userId, String phoneNumber);

    /**
     * Adds credits to a user
     *
     * @param credits number of credits to be added
     * @param userId  user id to be added credits to
     */
    void addCredits(long userId, int credits);

    /**
     * Gets a user by email fields
     *
     * @param email email of user
     * @return user if user found, null otherwise
     */
    User getByEmail(String email);

    /**
     * Gets the {@link User} by their username
     *
     * @param username the username to search for
     * @return the {@link User} with this username, null if not found
     */
    User getByUsername(String username);

    /**
     * Gets the {@link User} by their API token
     *
     * @param token the token to search for
     * @return the {@link User} with this token, null if not found
     */
    User getByToken(String token);
}
