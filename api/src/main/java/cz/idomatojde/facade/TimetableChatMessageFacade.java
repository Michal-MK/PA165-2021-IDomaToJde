package cz.idomatojde.facade;

import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;

import java.util.List;

/**
 * Facade responsible for all things concerning timetable chat messages
 *
 * @author Jiri Vrbka
 */
public interface TimetableChatMessageFacade {


    /**
     * Register a new timetable chat message
     *
     * @param timetableChatMessageDTO DTO with necessary information about chat message
     */
    long addTimetableChatMessage(AddTimetableChatMessageDTO timetableChatMessageDTO);

    /**
     * Gets timetable chat message by given id
     *
     * @param id unique identifier
     * @return DTO with information about timetable chat message
     */
    TimetableChatMessageDTO getTimetableChatMessageWithId(long id);

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
     * Deletes timetable chat message defined by given id
     *
     * @param id unique identifier for a timetable chat message
     */
    void deleteTimetableChatMessage(long id);

    /**
     * Gets all timetable chat messages belonging to entry with given id
     *
     * @param entryId unique identifier for TimetableEntry
     * @return all timetable chat messages owned by given TimetableEntry
     */
    List<TimetableChatMessageDTO> getAllMessagesOfTimetableEntry(long entryId);

}
