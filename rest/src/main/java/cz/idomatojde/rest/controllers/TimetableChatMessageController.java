package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.facade.TimetableChatMessageFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
public class TimetableChatMessageController extends
        AuthBaseRESTController<TimetableChatMessageFacade, AddTimetableChatMessageDTO, TimetableChatMessageDTO> {
    @Inject
    public TimetableChatMessageController(UserFacade userFacade, TimetableChatMessageFacade chatMessages) {
        super(userFacade, chatMessages);
    }

    @GetMapping("allMessagesOfUser?userId={userId}")
    List<TimetableChatMessageDTO> getMessagesByUserId(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        return facade.getAllMessagesOfUser(userId);
    }

    @GetMapping("allMessagesOfTimetableEntry?entryId={entryId}")
    List<TimetableChatMessageDTO> getAllMessagesOfTimetableEntry(@RequestHeader(value = "token") String token, @PathVariable long entryId) {
        return facade.getAllMessagesOfTimetableEntry(entryId);
    }

    @PostMapping("changeMessage")
    void changeMessage(@RequestHeader(value = "token") String token, ChangeTextTimetableChatMessageDTO dto) {
        facade.changeText(dto);
    }

    @PostMapping("deleteAllMessages?userId={userId}")
    void deleteMessagesByUserId(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        facade.deleteAllMessagesOfUser(userId);
    }
}
