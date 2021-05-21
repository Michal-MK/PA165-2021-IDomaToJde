package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.facade.TimetableChatMessageFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

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
    ResponseEntity<List<TimetableChatMessageDTO>> getMessagesByUserId(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        if (isAuthenticated(token) == null) return forbidden(null);
        return ok(facade.getAllMessagesOfUser(userId));
    }

    @GetMapping("allMessagesOfTimetableEntry?entryId={entryId}")
    ResponseEntity<List<TimetableChatMessageDTO>> getAllMessagesOfTimetableEntry(@RequestHeader(value = "token") String token, @PathVariable long entryId) {
        return ok(facade.getAllMessagesOfTimetableEntry(entryId));
    }

    @PostMapping("changeMessage")
    ResponseEntity<Void> changeMessage(@RequestHeader(value = "token") String token, ChangeTextTimetableChatMessageDTO dto) {
        facade.changeText(dto);
        return ok().build();
    }

    @PostMapping("deleteAllMessages?userId={userId}")
    ResponseEntity<Void> deleteMessagesByUserId(@RequestHeader(value = "token") String token, @PathVariable long userId) {
        facade.deleteAllMessagesOfUser(userId);
        return ok().build();
    }
}
