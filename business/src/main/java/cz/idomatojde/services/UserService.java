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
    void addPhone(String phoneNumber, Long userId);

    /**
     * Sets a new credit balance for a {@link User} identified the their ID
     *
     * @param credits the new amount of credits
     * @param userId  the ID of the {@link User}
     */
    void addCredits(Integer credits, Long userId);
}
