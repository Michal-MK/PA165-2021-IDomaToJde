package cz.idomatojde.services;

import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseService;

import java.util.List;

/**
 * Service class for {@link TimetableChatMessage}
 *
 * @author Jiri Vrbka
 */
public interface TimetableChatMessageService extends BaseService<TimetableChatMessage> {
    /**
     * Function to add a new message to a {@link TimetableEntry}
     *
     * @param sender the User who sent the message
     * @param entry  the {@link TimetableEntry} to hold the message
     * @param text   the content of the message
     */
    void addMessage(User sender, TimetableEntry entry, String text);


    /**
     * Function to obtain all {@link TimetableChatMessage} of a given {@link TimetableEntry}
     *
     * @param entry the entry to get messages from
     * @return a list of messages
     */
    List<TimetableChatMessage> getAllMessagesForEntry(TimetableEntry entry);

    /**
     * Function to obtain all {@link TimetableChatMessage} of a given {@link User}
     *
     * @param user the user to get messages from
     * @return a list of messages
     */
    List<TimetableChatMessage> getAllMessagesForUser(User user);

    /**
     * Function that removes all {@link TimetableChatMessage} of a given {@link User}
     *
     * @param user the user to remove messages from
     */
    void deleteAllMessagesOfUser(User user);
}
