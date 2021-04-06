package cz.idomatojde;

import cz.idomatojde.base.TestBase;
import cz.idomatojde.dao.ChatMessagesDAO;
import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.dao.TimetableDAO;
import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration("classpath:applicationConfig.xml")
@DataJpaTest
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class SimpleTest extends AbstractTestNGSpringContextTests {

    @Autowired
    public TimetableDAO timetableDao;

    @Autowired
    public ChatMessagesDAO chatMessagesDAO;

    @Autowired
    public OfferDao offerDAO;

    @Autowired
    public UserDao userDAO;


    @Test
    public void DaoTests() {
        User u = new User();
        u.setUsername("username");
        u.setPhoneNumber("11111");
        u.setEmail("emailsadasd");
        u.setPassHash("emailsadasd");
        u.setPassSalt("emailsadasd");
        u.setName("emailsadasd");
        u.setSurname("emailsadasd");
        u.setCredits(2);

        Offer o = new Offer();
        o.setId(2L);

        //em = emf.createEntityManager();

        //em.getTransaction().begin();

        userDAO.create(u);
        offerDAO.create(o);

        //em.getTransaction().commit();

        //em.getTransaction().begin();

        Timetable tt = timetableDao.createTimetable(u, 2021, 20);

        timetableDao.createEntry(tt,o,LocalTime.of(10,00), Duration.ofHours(2));

        //em.getTransaction().commit();

        var entries = timetableDao.getAllTimetableEntries(tt.getId());

        assertThat(entries.size()).isEqualTo(1);

        entries.get(0).setDescription("New Description");
        var msgs = entries.get(0).getMessages();
        timetableDao.updateEntry(entries.get(0));

        tt = timetableDao.getById(1L);

        assertThat(tt.getWeek()).isEqualTo(20);

        tt = timetableDao.getTimetable(u, 2021, 20);

        timetableDao.getAllTimetableEntries(tt.getId());

        assertThat(tt.getId()).isEqualTo(1L);
    }
}
