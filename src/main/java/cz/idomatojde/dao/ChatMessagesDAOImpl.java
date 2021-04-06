package cz.idomatojde.dao;

import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ChatMessagesDAOImpl implements ChatMessagesDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void addMessage(User sender, TimetableEntry entry, String text) {
        TimetableChatMessage message = new TimetableChatMessage();
        message.setSender(sender);
        message.setTimetableEntry(entry);
        message.setText(text);

        em.persist(message);
    }

    @Override
    @Transactional
    public void editMessage(Long messageId, String newText) {
        em.createQuery("update TimetableChatMessage m set m.text = :text where m.id = :id")
                .setParameter("text", newText)
                .setParameter("id", messageId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deleteMessage(TimetableChatMessage message) {
        em.remove(message);
    }

    @Override
    public List<TimetableChatMessage> getAllMessagesForEntry(TimetableEntry entry) {
       return em.createQuery("select tcm from TimetableChatMessage tcm where tcm.timetableEntry = :entry",
               TimetableChatMessage.class)
               .setParameter("entry", entry)
               .getResultList();
    }
}
