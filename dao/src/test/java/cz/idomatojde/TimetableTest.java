package cz.idomatojde;

import cz.idomatojde.dao.CategoryDao;
import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.dao.TimetableDAO;
import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.List;

import static cz.idomatojde.TestObjects.getOffer;
import static cz.idomatojde.TestObjects.getUser;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Inject
    private CategoryDao catDao;

    private Category category;


    @BeforeMethod
    public void setup() {
        category = new Category();
        category.setName("Category");
        catDao.create(category);
    }

    @Test
    public void createTimetableTest() {

        // Arrange
        var user = getUser("createTimetableTest");
        userDao.create(user);

        // Act
        var origin = timetableDAO.createTimetable(user, 2012, 2);
        var actual = timetableDAO.getById(origin.getId());

        // Assert
        assertThat(actual).isEqualTo(origin);
    }

    @Test
    public void createEntryTest() {
        // Arrange
        var offer = getOffer(category, "createEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        // Act
        var origin = timetableDAO.createEntry(timetable, 4, offer, LocalTime.now(), Duration.ofMinutes(50));
        var timetableEntryList = timetableDAO.getAllTimetableEntries(timetable.getId());

        // Assert
        assertThat(timetableEntryList).containsExactly(origin);
    }

    @Test
    public void moveEntryAndDurationTest() {
        // Arrange
        var updatedTime = LocalTime.now().minusHours(1);
        var updatedDuration = Duration.ofMinutes(10);
        var updatedDay = 5;

        var offer = getOffer(category, "moveEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        // Act
        var origin = timetableDAO.createEntry(timetable, 4, offer, LocalTime.now(), Duration.ofMinutes(50));
        timetableDAO.moveEntry(origin, updatedDay, updatedTime, updatedDuration);
        var updated = timetableDAO.findEntry(origin.getId());

        // Assert
        assertThat(updated.getEntryStart()).isEqualTo(updatedTime);
        assertThat(updated.getLength()).isEqualTo(updatedDuration);
        assertThat(updated.getDay()).isEqualTo(updatedDay);
    }

    @Test
    public void moveEntryTest() {
        // Arrange
        var updatedTime = LocalTime.now().minusHours(1);

        var offer = getOffer(category, "moveEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        // Act
        var origin = timetableDAO.createEntry(timetable, 4, offer, LocalTime.now(), Duration.ofMinutes(50));
        timetableDAO.moveEntry(origin, updatedTime);
        var updated = timetableDAO.findEntry(origin.getId());

        // Assert
        assertThat(updated.getEntryStart()).isEqualTo(updatedTime);
    }


    @Test
    public void removeEntryTest() {
        // Arrange
        var offer = getOffer(category, "removeEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var origin = timetableDAO.createEntry(timetable, 4, offer, LocalTime.now(), Duration.ofMinutes(50));

        // Assert
        List<TimetableEntry> entriesBefore = timetableDAO.getAllTimetableEntries(timetable.getId());

        assertThat(entriesBefore.size()).isEqualTo(1);

        // Act
        timetableDAO.removeEntry(origin);

        // Assert
        List<TimetableEntry> entriesAfter = timetableDAO.getAllTimetableEntries(timetable.getId());

        assertThat(entriesAfter).isEmpty();
    }


    @Test
    public void updateEntryTest() {
        // Arrange
        final String newDesc = "myDesc";
        final int newDay = 21;

        var offer = getOffer(category, "updateEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry = timetableDAO.createEntry(timetable, 4, offer, LocalTime.now(), Duration.ofMinutes(50));

        // Act
        entry.setDescription(newDesc);
        entry.setDay(newDay);
        timetableDAO.updateEntry(entry);

        // Assert
        var updatedEntry = timetableDAO.findEntry(entry.getId());
        var updatedDesc = updatedEntry.getDescription();
        var updatedDay = updatedEntry.getDay();

        assertThat(updatedDesc).isEqualTo(newDesc);
        assertThat(updatedDay).isEqualTo(newDay);
    }

    @Test
    public void getTimetableTest() {
        // Arrange
        var user = getUser("getTimetableTest");
        userDao.create(user);

        // Act
        var origin = timetableDAO.createTimetable(user, 2012, 2);
        var actual = timetableDAO.getById(origin.getId());

        // Assert
        assertThat(actual).isEqualTo(origin);
    }

    @Test
    public void findEntryTest() {
        // Arrange
        var offer = getOffer(category, "findEntryTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var entry1 = timetableDAO.createEntry(timetable, 4, offer, LocalTime.now(), Duration.ofMinutes(50));
        var entry2 = timetableDAO.createEntry(timetable, 4, offer, LocalTime.of(10, 10), Duration.ofMinutes(50));
        var entry3 = timetableDAO.createEntry(timetable, 4, offer, LocalTime.now(), Duration.ofMinutes(10));

        // Act
        var entryDb1 = timetableDAO.findEntry(entry1.getId());
        var entryDb2 = timetableDAO.findEntry(entry2.getId());
        var entryDb3 = timetableDAO.findEntry(entry3.getId());

        // Assert
        assertThat(entryDb1).isEqualTo(entry1);
        assertThat(entryDb2).isEqualTo(entry2);
        assertThat(entryDb3).isEqualTo(entry3);
    }

    @Test
    public void getTimetableForCurrentWeekTest() {
        // Arrange
        int week = LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int year = LocalDate.now().getYear();

        var offer = getOffer(category, "getTimetableForCurrentWeekTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        var timetableWrongYear = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);
        var timetableWrongWeek = timetableDAO.createTimetable(offer.getOwner(), year, week - 1);
        var timetableForCurrent = timetableDAO.createTimetable(offer.getOwner(), year, week);

        // Act
        var timetable = timetableDAO.getTimetableForCurrentWeek(offer.getOwner());

        // Assert
        assertThat(timetable).isEqualTo(timetableForCurrent);
        assertThat(timetable).isNotEqualTo(timetableWrongWeek);
        assertThat(timetable).isNotEqualTo(timetableWrongYear);

        assertThat(timetable.getYear()).isEqualTo(year);
        assertThat(timetable.getWeek()).isEqualTo(week);
        assertThat(timetable.getUser()).isEqualTo(offer.getOwner());
    }

    @Test
    public void getAllTimetableEntriesTest() {
        // Arrange
        var offer = getOffer(category, "getAllTimetableEntriesTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        var entry1 = timetableDAO.createEntry(timetable, 4, offer, LocalTime.now(), Duration.ofMinutes(50));
        var entry2 = timetableDAO.createEntry(timetable, 4, offer, LocalTime.of(10, 10), Duration.ofMinutes(50));
        var entry3 = timetableDAO.createEntry(timetable, 4, offer, LocalTime.now(), Duration.ofMinutes(10));

        // Act
        var entries = timetableDAO.getAllTimetableEntries(timetable.getId());

        // Assert
        assertThat(entries).containsExactly(entry1, entry2, entry3);
    }


    @Test
    public void updateTest() {
        // Arrange
        var newUser = getUser("newUserForUpdating");
        userDao.create(newUser);

        var offer = getOffer(category, "updateTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        var timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        // Act
        timetable.setUser(newUser);

        // Assert
        assertThat(timetable.getUser()).isEqualTo(newUser);
    }

    @Test
    public void getByIdWithUser() {
        // Arrange
        var offer = getOffer(category, "getAllTimetableEntriesTest");
        userDao.create(offer.getOwner());
        offerDao.create(offer);
        Timetable timetable = timetableDAO.createTimetable(offer.getOwner(), 2012, 2);

        // Act
        Timetable retrieved = timetableDAO.getByIdWithUser(timetable.getId());

        // Assert
        assertThat(timetable.getUser()).isNotNull();
        assertThat(timetable.getUser().getUsername()).isEqualTo(timetable.getUser().getUsername());
    }
}
