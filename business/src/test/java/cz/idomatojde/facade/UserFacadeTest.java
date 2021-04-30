package cz.idomatojde.facade;

import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.dto.user.UserCreditsDTO;
import cz.idomatojde.exceptions.InvalidPhoneNumberException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ContextConfiguration("classpath:applicationConfig.xml")
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

    @Inject
    UserFacade userFacade;

    @Test(expectedExceptions = InvalidPhoneNumberException.class)
    void userIntegration() {
        RegisterUserDTO userDto = new RegisterUserDTO();
        userDto.setUsername("testUser");
        userDto.setPassword("Argon2");
        userDto.setName("John");
        userDto.setSurname("Doe");
        userDto.setPhoneNumber("+420123456789");
        userDto.setEmail("a@a.cz");

        Long userId = userFacade.registerUser(userDto);

        UserContactInfoDTO contact = userFacade.getUserContactInfo(userId);
        assertThat(contact.getName()).isEqualTo("John");
        assertThat(contact.getSurname()).isEqualTo("Doe");
        assertThat(contact.getPhoneNumber()).isEqualTo("+420123456789");

        UserCreditsDTO credits = userFacade.getCredits(userId);

        assertThat(credits.getCredits()).isEqualTo(0);
        assertThat(credits.getBonusCredits()).isEqualTo(0);

        userFacade.setCredits(userId, 50);

        UserCreditsDTO editedCredits = userFacade.getCredits(userId);

        assertThat(editedCredits.getCredits()).isEqualTo(50);
        assertThat(editedCredits.getBonusCredits()).isEqualTo(0);

        userFacade.changePhoneNumber(userId, "+420987654321");

        UserContactInfoDTO updatedContact = userFacade.getUserContactInfo(userId);
        assertThat(updatedContact.getName()).isEqualTo("John");
        assertThat(updatedContact.getSurname()).isEqualTo("Doe");
        assertThat(updatedContact.getPhoneNumber()).isEqualTo("+420987654321");

        userFacade.changePhoneNumber(userId, "Invalid phone number!");
    }
}
