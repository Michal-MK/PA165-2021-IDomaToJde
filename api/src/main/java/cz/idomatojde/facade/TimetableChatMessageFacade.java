package cz.idomatojde.facade;

import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.facade.base.BaseFacade;

import java.util.List;

/**
 * Facade responsible for all things concerning timetable chat messages
 *
 * @author Jiri Vrbka
 */
public interface TimetableChatMessageFacade extends BaseFacade<AddTimetableChatMessageDTO, TimetableChatMessageDTO> {

    /**
     * Changes text of given timetable chat message
     *
     * @param changeTextTimetableChatMessageDTO DTO with necessary information
     */
    void changeText(ChangeTextTimetableChatMessageDTO changeTextTimetableChatMessageDTO);

    /**
     * Deletes all timetable chat messages that are owned by user with given id
     *
     * @param userId unique identifier for a user
     */
    void deleteAllMessagesOfUser(long userId);

    /**
     * Gets all timetable chat messages owned by user with given id
     *
     * @param userId unique identifier of a user
     * @return all timetable chat message owner by user
     */
    List<TimetableChatMessageDTO> getAllMessagesOfUser(long userId);

    /**
     * Gets all timetable chat messages belonging to entry with given id
     *
     * @param entryId unique identifier for TimetableEntry
     * @return all timetable chat messages owned by given TimetableEntry
     */
    List<TimetableChatMessageDTO> getAllMessagesOfTimetableEntry(long entryId);

}
