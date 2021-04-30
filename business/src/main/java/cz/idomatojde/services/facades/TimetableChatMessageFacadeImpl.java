package cz.idomatojde.services.facades;

import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import cz.idomatojde.facade.TimetableChatMessageFacade;
import cz.idomatojde.services.TimetableChatMessageService;
import cz.idomatojde.services.TimetableService;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.base.MappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Jiri Vrbka
 */
@Service
@Transactional
public class TimetableChatMessageFacadeImpl implements TimetableChatMessageFacade {

    @Inject
    private MappingService mappingService;

    @Inject
    private UserService userService;

    @Inject
    private TimetableService timetableService;

    @Inject
    private TimetableChatMessageService timetableChatMessageService;

    @Override
    public void addTimetableChatMessage(AddTimetableChatMessageDTO timetableChatMessageDTO) {
        var user = mappingService.mapTo(timetableChatMessageDTO.getSender(), User.class);
        var entry = mappingService.mapTo(timetableChatMessageDTO.getTimetableEntry(), TimetableEntry.class);;
        var text = timetableChatMessageDTO.getText();
        timetableChatMessageService.addMessage(user, entry, text);
    }

    @Override
    public TimetableChatMessageDTO getTimetableChatMessageWithId(long id) {
        var msg = timetableChatMessageService.getById(id);
        return mappingService.mapTo(msg, TimetableChatMessageDTO.class);
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
        var messages = timetableChatMessageService.getAllMessagesForUser(user);
        return mappingService.mapTo(messages, TimetableChatMessageDTO.class);
    }

    @Override
    public void deleteTimetableChatMessage(long id) {
        var msg = timetableChatMessageService.getById(id);
        timetableChatMessageService.delete(msg);
    }

    @Override
    public List<TimetableChatMessageDTO> getAllMessagesOfTimetableEntry(long entryId) {
         var entry = timetableService.findEntry(entryId);
         var messages = timetableChatMessageService.getAllMessagesForEntry(entry);
        return mappingService.mapTo(messages, TimetableChatMessageDTO.class);
    }
}
