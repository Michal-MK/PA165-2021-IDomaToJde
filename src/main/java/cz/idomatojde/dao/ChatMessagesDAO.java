package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;

import java.util.List;

public interface ChatMessagesDAO extends BaseDAO<TimetableChatMessage> {

    void addMessage(User sender, TimetableEntry entry, String text);

    List<TimetableChatMessage> getAllMessagesForEntry(TimetableEntry entry);
}
