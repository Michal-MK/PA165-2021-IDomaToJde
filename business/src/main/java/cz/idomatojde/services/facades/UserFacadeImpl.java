package cz.idomatojde.services.facades;

import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.entity.User;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.base.MappingService;
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
    public void registerUser(RegisterUserDTO registrationInfo) {
        User u = mapService.mapTo(registrationInfo, User.class);
        userService.create(u);
    }

    @Override
    public UserContactInfoDTO getUserContactInfo(long userId) {
        User u = userService.getById(userId);

        return mapService.mapTo(u, UserContactInfoDTO.class);
    }
}
