package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.facade.TimetableChatMessageFacade;
import cz.idomatojde.rest.controllers.base.BaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Controller responsible for all things concerning TimetableChatMessages
 *
 * @author Michal Hazdra
 */
@Api(tags = "TimetableChatMessages Endpoint")
@RestController
@RequestMapping("timetableChatMessages")
public class TimetableChatMessageController extends BaseRESTController<TimetableChatMessageFacade, AddTimetableChatMessageDTO, TimetableChatMessageDTO> {
    @Inject
    public TimetableChatMessageController(TimetableChatMessageFacade chatMessages) {
        super(chatMessages);
    }

    @GetMapping("allMessagesOfUser?userId={userId}")
    List<TimetableChatMessageDTO> getMessagesByUserId(@PathVariable long userId){
        return facade.getAllMessagesOfUser(userId);
    }

    @GetMapping("allMessagesOfTimetableEntry?entryId={entryId}")
    List<TimetableChatMessageDTO> getAllMessagesOfTimetableEntry(@PathVariable long entryId){
        return facade.getAllMessagesOfTimetableEntry(entryId);
    }

    @PostMapping("changeMessage")
    void changeMessage(ChangeTextTimetableChatMessageDTO dto){
        facade.changeText(dto);
    }

    @PostMapping("deleteAllMessages?userId={userId}")
    void deleteMessagesByUserId(@PathVariable long userId){
        facade.deleteAllMessagesOfUser(userId);
    }
}
