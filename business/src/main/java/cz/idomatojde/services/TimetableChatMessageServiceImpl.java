package cz.idomatojde.services;

import cz.idomatojde.dao.TimetableChatMessageDAO;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Jiri Vrbka
 */
@Service
public class TimetableChatMessageServiceImpl extends BaseServiceImpl<TimetableChatMessage> implements TimetableChatMessageService {

    private final TimetableChatMessageDAO chatMessages;

    @Inject
    public TimetableChatMessageServiceImpl(TimetableChatMessageDAO chatMessages) {
        super(chatMessages);
        this.chatMessages = chatMessages;
    }

    @Override
    public void addMessage(User sender, TimetableEntry entry, String text) {
        chatMessages.addMessage(sender, entry, text);
    }

    @Override
    public List<TimetableChatMessage> getAllMessagesForEntry(TimetableEntry entry) {
        return chatMessages.getAllMessagesForEntry(entry);
    }

    @Override
    public List<TimetableChatMessage> getAllMessagesForUser(User user) {
        return chatMessages.getAllMessagesOfUser(user);
    }

    @Override
    public void deleteAllMessagesOfUser(User user) {
        var messages = chatMessages.getAllMessagesOfUser(user);
        for (var message : messages) {
            chatMessages.delete(message);
        }
    }
}
