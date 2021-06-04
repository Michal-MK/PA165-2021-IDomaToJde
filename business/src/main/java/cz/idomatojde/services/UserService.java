package cz.idomatojde.services;

import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseService;

/**
 * Service class for {@link User}
 *
 * @author Michal Hazdra
 */
public interface UserService extends BaseService<User> {
    /**
     * Assigns a phone number to a {@link User} identified the their ID
     *
     * @param phoneNumber the phone number to assign
     * @param userId      the ID of the {@link User}
     */
    void addPhone(long userId, String phoneNumber);

    /**
     * Sets a new credit balance for a {@link User} identified the their ID
     *
     * @param credits the new amount of credits
     * @param userId  the ID of the {@link User}
     */
    void addCredits(long userId, int credits);

    /**
     * Attempts to authenticate a user using the provided credentials
     *
     * @param username the user's username
     * @param pass     the user's password
     * @return true is authenticated successfully, false otherwise
     */
    boolean authenticate(String username, String pass);

    /**
     * Attempts to authenticate a user using the provided credentials
     *
     * @param token the user's username
     * @return {@link User} object if authenticated successfully, null otherwise
     */
    User authenticate(String token);

    /**
     * Stores a token for API authentication
     *
     * @param username the username this token belongs to
     * @param token    tje newly generated token
     */
    void saveToken(String username, String token);
}
