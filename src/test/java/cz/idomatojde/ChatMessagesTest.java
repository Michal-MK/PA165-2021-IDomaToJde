package cz.idomatojde;

import cz.idomatojde.dao.ChatMessagesDAO;
import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.dao.TimetableDAO;
import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.User;
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
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Jiri Vrbka
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@DataJpaTest
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ChatMessagesTest extends AbstractTestNGSpringContextTests {


    @Inject
    private ChatMessagesDAO chatMessagesDAO;

    @Inject
    private TimetableDAO timetableDAO;

    @Inject
    private OfferDao offerDao;

    @Inject
    private UserDao userDao;

    private User getUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassHash("UGFzc3dvcmQ=");
        user.setPassSalt("U2FsdA==");
        user.setName("Name");
        user.setSurname("Surname");
        user.setPhoneNumber("+420123456789");
        user.setEmail("pepega@mail.com");
        user.setCredits(123);
        user.setWantsAdvertisement(false);
        user.setAdmin(false);

        return user;
    }


    private Offer getOffer(String title) {
        Offer offer = new Offer();
        offer.setTitle(title);
        offer.setOwner(getUser(title));
        offer.setDescription("description");
        offer.setCategory(Category.EDUCATION);
        offer.setCapacity(10);
        offer.setRegistered(5);
        offer.setPrice(BigDecimal.ONE);
        offer.setCreatedDate(LocalDate.of(2021, 4, 1));
        offer.setExpirationDate(LocalDate.of(2021,4,20));

        return offer;
    }


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
        chatMessagesDAO.addMessage(offer.getOwner(), entry, msg);

        // Assert
        var chats = chatMessagesDAO.getAllMessagesForEntry(entry);

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

        chatMessagesDAO.create(chat1);


        var chat2 = new TimetableChatMessage();
        chat2.setText("msg2");
        chat2.setSender(offer.getOwner());
        chat2.setTimetableEntry(entry);

        chatMessagesDAO.create(chat2);

        // Act
        var chats = chatMessagesDAO.getAllMessagesForEntry(entry);

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

        chatMessagesDAO.create(chat);

        // Act
        chat.setText(msgUpdated);
        chatMessagesDAO.update(chat);
        var chatDb = chatMessagesDAO.getById(chat.getId());

        // Assert
        Assert.assertEquals(msgUpdated, chatDb.getText());
        Assert.assertEquals(chat, chatDb);
    }

    @Test
    public void createTest(){
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

        chatMessagesDAO.create(chat);
        var chatDb = chatMessagesDAO.getById(chat.getId());

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

        chatMessagesDAO.create(chat1);


        var chat2 = new TimetableChatMessage();
        chat2.setText(msg);
        chat2.setSender(offer.getOwner());
        chat2.setTimetableEntry(entry);

        chatMessagesDAO.create(chat2);

        var chats = chatMessagesDAO.findAll();

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

        chatMessagesDAO.create(chat);
        var chatDb = chatMessagesDAO.getById(chat.getId());

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

        chatMessagesDAO.create(chat);

        // Act
        chatMessagesDAO.delete(chat);

        // Assert
        chatMessagesDAO.getById(chat.getId());
    }
}
