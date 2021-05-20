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
import cz.idomatojde.services.facades.base.BaseFacadeImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author Michal Hazdra
 */
@Service
@Transactional
public class UserFacadeImpl extends BaseFacadeImpl<RegisterUserDTO, UserDTO, User> implements UserFacade {

    private final UserService userService;

    @Inject
    public UserFacadeImpl(UserService userService, MappingService mapService) {
        super(userService, mapService, UserDTO.class, User.class);
        this.userService = userService;
    }

    @Override
    public UserDTO getById(long id) {
        var user = userService.getById(id);
        var dto = mapService.toUserDTO(user);
        dto.setId(id);
        return dto;
    }

    @Override
    public UserContactInfoDTO getUserContactInfo(long userId) {
        User u = userService.getById(userId);

        return mapService.toUserContactInfoDTO(u);
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
