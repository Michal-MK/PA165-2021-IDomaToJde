package cz.idomatojde;

import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.dao.TimetableDAO;
import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
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
import java.time.temporal.ChronoField;

/**
 * @author Jiri Vrbka
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@DataJpaTest
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimetableTest extends AbstractTestNGSpringContextTests {

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
        offer.setOwner(UserTests.getUser(title));
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
    public void createTimetableTest(){

        // Arrange
        var user = getUser("createTimetableTest");
        userDao.create(user);

        // Act
        var origin = timetableDAO.createTimetable(user, 2012, 2);
        var actual = timetableDAO.getById(origin.getId());

        // Assert
        Assert.assertEquals(origin, actual);
    }


    @Test
    public void createEntryTest(){
        // Arrange
        var offer = getOffer("createEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        // Act
        var origin = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var timetableEntryList = timetableDAO.getAllTimetableEntries(timetable.getId());

        // Assert
        Assert.assertEquals(1, timetableEntryList.size());
        Assert.assertEquals(origin, timetableEntryList.get(0));
    }

    @Test
    public void moveEntryAndDurationTest(){
        // Arrange
        var updatedTime = LocalTime.now().minusHours(1);
        var updatedDuration = Duration.ofMinutes(10);

        var offer = getOffer("moveEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        // Act
        var origin = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        timetableDAO.moveEntry(origin, updatedTime, updatedDuration);
        var updated = timetableDAO.findEntry(origin.getId());

        // Assert
        Assert.assertEquals(updatedTime, updated.getEntryStart());
        Assert.assertEquals(updatedDuration, updated.getLength());
    }

    @Test
    public void moveEntryTest(){
        // Arrange
        var updatedTime = LocalTime.now().minusHours(1);

        var offer = getOffer("moveEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        // Act
        var origin = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        timetableDAO.moveEntry(origin, updatedTime);
        var updated = timetableDAO.findEntry(origin.getId());

        // Assert
        Assert.assertEquals(updatedTime, updated.getEntryStart());
    }


    @Test(expectedExceptions = javax.persistence.NoResultException.class)
    public void removeEntryTest(){
        // Arrange
        var offer = getOffer("removeEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var origin = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));

        // Act
        timetableDAO.removeEntry(origin);

        // Assert
        timetableDAO.getAllTimetableEntries(timetable.getId());
    }


    @Test
    public void updateEntryTest(){
        // Arrange
        final String newDesc = "myDesc";
        final int newDay = 21;

        var offer = getOffer("updateEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));

        // Act
        entry.setDescription(newDesc);
        entry.setDay(newDay);
        timetableDAO.updateEntry(entry);

        // Assert
        var updatedEntry = timetableDAO.findEntry(entry.getId());
        var updatedDesc = updatedEntry.getDescription();
        var updatedDay = updatedEntry.getDay();

        Assert.assertEquals(newDesc, updatedDesc);
        Assert.assertEquals(newDay, updatedDay);
    }

    @Test
    public void getTimetableTest(){
        // Arrange
        var user = getUser("getTimetableTest");
        userDao.create(user);

        // Act
        var origin = timetableDAO.createTimetable(user, 2012, 2);
        var actual = timetableDAO.getById(origin.getId());

        // Assert
        Assert.assertEquals(origin, actual);
    }

    @Test
    public void findEntryTest(){
        // Arrange
        var offer = getOffer("findEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry1 = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var entry2 = timetableDAO.createEntry(timetable, offer, LocalTime.of(10,10), Duration.ofMinutes(50));
        var entry3 = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(10));

        // Act
        var entryDb1 = timetableDAO.findEntry(entry1.getId());
        var entryDb2 = timetableDAO.findEntry(entry2.getId());
        var entryDb3 = timetableDAO.findEntry(entry3.getId());

        // Assert
        Assert.assertEquals(entry1, entryDb1);
        Assert.assertEquals(entry2, entryDb2);
        Assert.assertEquals(entry3, entryDb3);
    }

    @Test
    public void getTimetableForCurrentWeekTest(){
        // Arrange
        int week = LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int year = LocalDate.now().getYear();

        var offer = getOffer("getTimetableForCurrentWeekTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        var timetableWrongYear = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var timetableWrongWeek = timetableDAO.createTimetable(offer.getOwner(), year, week-1);
        var timetableForCurrent = timetableDAO.createTimetable(offer.getOwner(), year, week);

        // Act
        var timetable = timetableDAO.getTimetableForCurrentWeek(offer.getOwner());

        // Assert
        Assert.assertEquals(timetableForCurrent, timetable);
        Assert.assertNotEquals(timetableWrongWeek, timetable);
        Assert.assertNotEquals(timetableWrongYear, timetable);

        Assert.assertEquals(year, timetable.getYear());
        Assert.assertEquals(week, timetable.getWeek());
        Assert.assertEquals(offer.getOwner(), timetable.getUser());
    }

    @Test
    public void getAllTimetableEntriesTest(){
        // Arrange
        var offer = getOffer("getAllTimetableEntriesTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        var entry1 = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(50));
        var entry2 = timetableDAO.createEntry(timetable, offer, LocalTime.of(10,10), Duration.ofMinutes(50));
        var entry3 = timetableDAO.createEntry(timetable, offer, LocalTime.now(), Duration.ofMinutes(10));

        // Act
        var entries = timetableDAO.getAllTimetableEntries(timetable.getId());

        // Assert
        Assert.assertEquals(3, entries.size());
        Assert.assertTrue(entries.contains(entry1));
        Assert.assertTrue(entries.contains(entry2));
        Assert.assertTrue(entries.contains(entry3));
    }


    @Test
    public void updateTest(){
        // Arrange
        var newUser = getUser("newUser");
        userDao.create(newUser);

        var offer = getOffer("updateTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        // Act
        timetable.setUser(newUser);
        timetableDAO.update(timetable);

        // Assert
        Assert.assertEquals(newUser, timetable.getUser());
    }

}
