package cz.idomatojde.facade;

import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.dto.user.UserCreditsDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.exceptions.InvalidPhoneNumberException;

/**
 * Facade responsible for all things concerning users
 *
 * @author Michal Hazdra
 */
public interface UserFacade {

    /**
     * Register a new user
     *
     * @param registrationInfo necessary user creation information
     * @return the ID of the newly created entity
     */
    long registerUser(RegisterUserDTO registrationInfo);

    /**
     * Gets a user
     *
     * @param id the unique user identifier
     * @return DTO for user
     */
    UserDTO getById(long id);

    /**
     * Retrieve user's full name and contact information
     *
     * @param userId the unique user identifier
     * @return user's full name, phone number and e-mail account
     */
    UserContactInfoDTO getUserContactInfo(long userId);

    /**
     * Set a new amount of credits to a user
     *
     * @param userId  the unique user identifier
     * @param credits the amount of credits to set
     */
    void setCredits(long userId, int credits);

    /**
     * Set a new amount of credits to a user
     *
     * @param userId the unique user identifier
     * @return the total available credits for a User
     */
    UserCreditsDTO getCredits(long userId);

    /**
     * Set a new phone number to a user
     *
     * @param userId      the unique user identifier
     * @param phoneNumber the phone number to set
     */
    void changePhoneNumber(long userId, String phoneNumber) throws InvalidPhoneNumberException;
}
