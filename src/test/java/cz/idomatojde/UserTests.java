package cz.idomatojde;

import cz.idomatojde.dao.UserDao;
import cz.idomatojde.dao.Utils.UserContactInfo;
import cz.idomatojde.entity.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/** User specific DAO tests
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@DataJpaTest
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserTests extends AbstractTestNGSpringContextTests {

    @Inject
    public UserDao dao;

    public static User getUser(String username) {
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

    @Test
    public void userCreation() {
        //Setup
        User user = getUser("pepega");

        //Act
        dao.create(user);

        //Validate

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(dao.getById(1L).getUsername()).isEqualTo("pepega");
    }

    @Test
    public void userCredits() {
        //Setup
        User user = getUser("nootlikethis");

        //Act
        dao.create(user);

        //Validate
        assertThat(dao.getById(1L).getCredits()).isEqualTo(123);

        //Act
        dao.addCredits(1,user.getId());

        //Validate
        assertThat(user.getCredits()).isEqualTo(124);
    }

    @Test
    public void userPhoneNumber() {
        //Setup
        User user = getUser("knockknock");
        final String phone = "+420987654321";

        //Act
        dao.create(user);

        //Validate
        assertThat(dao.getById(1L).getPhoneNumber()).isEqualTo("+420123456789");

        //Act
        dao.addPhone(phone, user.getId());

        //Validate
        assertThat(user.getPhoneNumber()).isEqualTo(phone);
        assertThat(dao.getById(user.getId()).getPhoneNumber()).isEqualTo(phone);
    }

    @Test
    public void userContactInfo() {
        //Setup
        User user = getUser("thisUsedToBeAPublicFieldClass");

        //Act
        dao.create(user);

        UserContactInfo contact = dao.getContactInfo(user.getId());

        //Validate
        assertThat(contact.name).isEqualTo("Name");
        assertThat(contact.surname).isEqualTo("Surname");
        assertThat(contact.email).isEqualTo("pepega@mail.com");
        assertThat(contact.phone).isEqualTo("+420123456789");
    }
}
