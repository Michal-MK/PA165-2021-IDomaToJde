package cz.idomatojde.facade;

import cz.idomatojde.dto.AuthDTO;
import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.dto.user.UserCreditsDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.exceptions.InvalidPhoneNumberException;
import cz.idomatojde.facade.base.BaseFacade;

/**
 * Facade responsible for all things concerning users
 *
 * @author Michal Hazdra
 */
public interface UserFacade extends BaseFacade<RegisterUserDTO, UserDTO> {

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

    /**
     * Simple authentication method using username/password
     *
     * @param username the username of the user
     * @param pass     the password of the user
     * @return {@link AuthDTO} with the token if successful, with empty token if unsuccessful
     */
    AuthDTO authenticate(String username, String pass);

    /**
     * Simple authentication method using a token
     *
     * @param token the token of the user
     * @return {@link UserDTO} with the token if successful, null otherwise
     */
    UserDTO authenticate(String token);

    /**
     * Store a token generated for the REST API
     *
     * @param username the username this token belongs to
     * @param token    the new token
     */
    void saveToken(String username, String token);
}
