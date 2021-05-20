package cz.idomatojde.services.facades;

import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.facade.TimetableChatMessageFacade;
import cz.idomatojde.services.TimetableChatMessageService;
import cz.idomatojde.services.TimetableService;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.base.MappingService;
import cz.idomatojde.services.facades.base.BaseFacadeImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiri Vrbka
 */
@Service
@Transactional
public class TimetableChatMessageFacadeImpl
        extends BaseFacadeImpl<AddTimetableChatMessageDTO, TimetableChatMessageDTO, TimetableChatMessage>
        implements TimetableChatMessageFacade {

    private final UserService userService;

    private final TimetableService timetableService;

    private final TimetableChatMessageService timetableChatMessageService;

    @Inject
    public TimetableChatMessageFacadeImpl(UserService userService, TimetableService timetableService,
                                          TimetableChatMessageService chatMessageService, MappingService mapService) {
        super(chatMessageService, mapService, TimetableChatMessageDTO.class, TimetableChatMessage.class);
        this.userService = userService;
        this.timetableService = timetableService;
        this.timetableChatMessageService = chatMessageService;
    }

    @Override
    public void changeText(ChangeTextTimetableChatMessageDTO changeTextTimetableChatMessageDTO) {
        var msg = timetableChatMessageService.getById(changeTextTimetableChatMessageDTO.getId());
        msg.setText(changeTextTimetableChatMessageDTO.getText());
    }

    @Override
    public void deleteAllMessagesOfUser(long userId) {
        var user = userService.getById(userId);
        timetableChatMessageService.deleteAllMessagesOfUser(user);
    }

    @Override
    public List<TimetableChatMessageDTO> getAllMessagesOfUser(long userId) {
        var user = userService.getById(userId);

        return timetableChatMessageService.getAllMessagesForUser(user).stream()
                .map(mapService::toTimetableChatMessageDTO).collect(Collectors.toList());
    }

    @Override
    public List<TimetableChatMessageDTO> getAllMessagesOfTimetableEntry(long entryId) {
        var entry = timetableService.findEntry(entryId);

        return timetableChatMessageService.getAllMessagesForEntry(entry).stream()
                .map(mapService::toTimetableChatMessageDTO).collect(Collectors.toList());
    }
}
