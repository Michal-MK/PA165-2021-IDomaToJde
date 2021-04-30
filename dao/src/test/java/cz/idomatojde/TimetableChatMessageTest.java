package cz.idomatojde;

import cz.idomatojde.dao.TimetableChatMessageDAO;
import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.dao.TimetableDAO;
import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.TimetableChatMessage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalTime;

import static cz.idomatojde.TestObjects.getOffer;
import static cz.idomatojde.TestObjects.getUser;

/**
 * @author Jiri Vrbka
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@DataJpaTest
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimetableChatMessageTest extends AbstractTestNGSpringContextTests {


    @Inject
    private TimetableChatMessageDAO timetableChatMessageDAO;

    @Inject
    private TimetableDAO timetableDAO;

    @Inject
    private OfferDao offerDao;

    @Inject
    private UserDao userDao;


    @Test
    public void addMessageTest() {
        // Arrange
        var offer = getOffer("updateTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var msg = "my message";

        // Act
        timetableChatMessageDAO.addMessage(offer.getOwner(), entry, msg);

        // Assert
        var chats = timetableChatMessageDAO.getAllMessagesForEntry(entry);

        Assert.assertEquals(1, chats.size());

        var chat = chats.get(0);
        Assert.assertEquals(msg, chat.getText());
        Assert.assertEquals(offer.getOwner(), chat.getSender());
        Assert.assertEquals(entry, chat.getTimetableEntry());
    }

    @Test
    public void getAllMessagesForEntryTest() {
        // Arrange
        var offer = getOffer("getAllMessagesForEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));

        var chat1 = new TimetableChatMessage();
        chat1.setText("msg");
        chat1.setSender(offer.getOwner());
        chat1.setTimetableEntry(entry);

        timetableChatMessageDAO.create(chat1);


        var chat2 = new TimetableChatMessage();
        chat2.setText("msg2");
        chat2.setSender(offer.getOwner());
        chat2.setTimetableEntry(entry);

        timetableChatMessageDAO.create(chat2);

        // Act
        var chats = timetableChatMessageDAO.getAllMessagesForEntry(entry);

        // Assert
        Assert.assertEquals(2, chats.size());
        Assert.assertTrue(chats.contains(chat1));
        Assert.assertTrue(chats.contains(chat2));
    }

    @Test
    public void updateTest() {
        // Arrange
        var offer = getOffer("updateTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var msgUpdated = "my message";

        var chat = new TimetableChatMessage();
        chat.setText("message");
        chat.setSender(offer.getOwner());
        chat.setTimetableEntry(entry);

        timetableChatMessageDAO.create(chat);

        // Act
        chat.setText(msgUpdated);
        var chatDb = timetableChatMessageDAO.getById(chat.getId());

        // Assert
        Assert.assertEquals(msgUpdated, chatDb.getText());
        Assert.assertEquals(chat, chatDb);
    }

    @Test
    public void createTest() {
        // Arrange
        var offer = getOffer("createEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var msg = "my message";

        // Act
        var chat = new TimetableChatMessage();
        chat.setText(msg);
        chat.setSender(offer.getOwner());
        chat.setTimetableEntry(entry);

        timetableChatMessageDAO.create(chat);
        var chatDb = timetableChatMessageDAO.getById(chat.getId());

        // Assert
        Assert.assertEquals(chat, chatDb);
    }

    @Test
    public void findAllTest() {
        // Arrange
        var offer = getOffer("findAllTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var msg = "my message";

        // Act
        var chat1 = new TimetableChatMessage();
        chat1.setText(msg);
        chat1.setSender(offer.getOwner());
        chat1.setTimetableEntry(entry);

        timetableChatMessageDAO.create(chat1);


        var chat2 = new TimetableChatMessage();
        chat2.setText(msg);
        chat2.setSender(offer.getOwner());
        chat2.setTimetableEntry(entry);

        timetableChatMessageDAO.create(chat2);

        var chats = timetableChatMessageDAO.findAll();

        // Assert
        Assert.assertEquals(2, chats.size());
        Assert.assertTrue(chats.contains(chat1));
        Assert.assertTrue(chats.contains(chat2));
    }


    @Test
    public void getByIdTest() {
        // Arrange
        var offer = getOffer("getByIdTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var msg = "my message";

        // Act
        var chat = new TimetableChatMessage();
        chat.setText(msg);
        chat.setSender(offer.getOwner());
        chat.setTimetableEntry(entry);

        timetableChatMessageDAO.create(chat);
        var chatDb = timetableChatMessageDAO.getById(chat.getId());

        // Assert
        Assert.assertEquals(chat, chatDb);
    }

    @Test(expectedExceptions = javax.persistence.NoResultException.class)
    public void deleteTest() {
        // Arrange
        var offer = getOffer("deleteTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var msg = "my message";

        var chat = new TimetableChatMessage();
        chat.setText(msg);
        chat.setSender(offer.getOwner());
        chat.setTimetableEntry(entry);

        timetableChatMessageDAO.create(chat);

        // Act
        timetableChatMessageDAO.delete(chat);

        // Assert
        timetableChatMessageDAO.getById(chat.getId());
    }

    @Test
    public void getAllMessagesOfUser(){
        // Arrange
        var offer = getOffer("get all messages");
        var anotherUser = getUser("another");
        userDao.create(offer.getOwner());
        userDao.create(anotherUser);
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var msg = "my message";

        var chat = new TimetableChatMessage();
        chat.setText(msg);
        chat.setSender(offer.getOwner());
        chat.setTimetableEntry(entry);

        var anotherChat = new TimetableChatMessage();
        anotherChat.setText("text");
        anotherChat.setSender(anotherUser);
        anotherChat.setTimetableEntry(entry);

        timetableChatMessageDAO.create(chat);
        timetableChatMessageDAO.create(anotherChat);

        // Act
        var messages = timetableChatMessageDAO.getAllMessagesOfUser(offer.getOwner());

        // Assert
        Assert.assertEquals(messages.size(), 1);
        Assert.assertEquals(messages.get(0), chat);

    }
}
