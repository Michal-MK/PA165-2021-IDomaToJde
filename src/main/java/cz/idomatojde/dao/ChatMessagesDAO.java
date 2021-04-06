package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;

import java.util.List;

/**
 * API for TimetableChatMessage DTOs
 * @author Michal Hazdra
 */
public interface ChatMessagesDAO extends BaseDAO<TimetableChatMessage> {

    /** Function to add a new message to a TimetableEntry
     * @param sender the User who sent the message
     * @param entry the TimetableEntry to hold the message
     * @param text the content of the message
     */
    void addMessage(User sender, TimetableEntry entry, String text);


    /** Function to obtain all TimetableChatMessages of a given TimetableEntry
     * @param entry the entry to get messages from
     * @return a list of messages
     */
    List<TimetableChatMessage> getAllMessagesForEntry(TimetableEntry entry);
}
