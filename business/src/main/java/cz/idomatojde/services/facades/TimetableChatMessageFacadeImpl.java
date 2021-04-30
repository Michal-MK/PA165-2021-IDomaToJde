package cz.idomatojde.services.facades;

import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.facade.TimetableChatMessageFacade;
import cz.idomatojde.services.TimetableChatMessageService;
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
    private TimetableChatMessageService timetableChatMessageService;

    @Override
    public long addTimetableChatMessage(AddTimetableChatMessageDTO timetableChatMessageDTO) {
        // TODO missing timetableEntryService and userService
        return 0;
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
        // TODO missing user service
        // var user = userService.getById(userId);
        // timetableChatMessageService.deleteAllMessagesOfUser(user);
    }

    @Override
    public List<TimetableChatMessageDTO> getAllMessagesOfUser(long userId) {
        // TODO missing user service
        // var user = userService.getById(userId);
        // return timetableChatMessageService.getAllMessagesForUser(user)
        return null;
    }

    @Override
    public void deleteTimetableChatMessage(long id) {
        var msg = timetableChatMessageService.getById(id);
        timetableChatMessageService.delete(msg);
    }

    @Override
    public List<TimetableChatMessageDTO> getAllMessagesOfTimetableEntry(long entryId) {
        // TODO missing entry service
        // var entry = entryService.getById(entryId);
        // return timetableChatMessageService.getAllMessagesForEntry(entry);
        return null;
    }
}
