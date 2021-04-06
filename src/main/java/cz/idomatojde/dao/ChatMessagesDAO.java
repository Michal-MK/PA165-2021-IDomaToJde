package cz.idomatojde.dao;

import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;

import java.util.List;

public interface ChatMessagesDAO {
    void addMessage(User sender, TimetableEntry entry, String text);

    void editMessage(Long messageId, String newText);

    void deleteMessage(TimetableChatMessage message);

    List<TimetableChatMessage> getAllMessagesForEntry(TimetableEntry entry);
}
