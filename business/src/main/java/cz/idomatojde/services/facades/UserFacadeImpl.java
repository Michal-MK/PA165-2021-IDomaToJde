package cz.idomatojde.services.facades;

import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.dto.user.UserCreditsDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.entity.User;
import cz.idomatojde.exceptions.InvalidPhoneNumberException;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.base.MappingService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    private final MappingService mapService;

    @Inject
    public UserFacadeImpl(UserService userService, MappingService mapService) {
        this.userService = userService;
        this.mapService = mapService;
    }

    @Override
    public Long registerUser(RegisterUserDTO registrationInfo) {
        User u = mapService.mapTo(registrationInfo, User.class);

        u.setCredits(0);
        u.setBonusCredits(0);
        u.setPassword(new Argon2PasswordEncoder().encode(registrationInfo.getPassword()));

        return userService.create(u);
    }

    @Override
    public UserDTO getById(long id) {
        var user = userService.getById(id);
        return mapService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserContactInfoDTO getUserContactInfo(long userId) {
        User u = userService.getById(userId);

        return mapService.mapTo(u, UserContactInfoDTO.class);
    }

    @Override
    public void setCredits(long userId, int credits) {
        User u = userService.getById(userId);

        u.setCredits(credits);
    }

    @Override
    public UserCreditsDTO getCredits(long userId) {
        User u = userService.getById(userId);

        return new UserCreditsDTO(u.getCredits(), u.getBonusCredits());
    }

    @Override
    public void changePhoneNumber(long userId, String phoneNumber) {
        User u = userService.getById(userId);

        String phoneNumPattern = "(?:\\+\\d{1,3}|0\\d{1,3}|00\\d{1,2})?(?:\\s?\\(\\d+\\))?(?:[-/\\s.]|\\d)+";

        if (phoneNumber.matches(phoneNumPattern)) {
            u.setPhoneNumber(phoneNumber);
        } else {
            throw new InvalidPhoneNumberException("The phone number: '" + phoneNumber + "' was not recognized!");
        }
    }
}
