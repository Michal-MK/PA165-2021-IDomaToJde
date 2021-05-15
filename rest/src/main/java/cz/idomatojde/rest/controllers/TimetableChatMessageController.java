package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.facade.TimetableChatMessageFacade;
import cz.idomatojde.rest.controllers.base.BaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

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
}
