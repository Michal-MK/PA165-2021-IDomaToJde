package cz.idomatojde.facade;

import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration("classpath:applicationConfig.xml")
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class OfferFacadeTest extends AbstractTestNGSpringContextTests {

    @Inject
    private UserFacade userFacade;

    @Inject
    private OfferFacade offerFacade;

    @Test
    public void registerOfferAndGetOfferWithId(){
        // Setup
        var userDto = getUserDTO();
        var registerDto = getRegisterOffer(userDto);

        // Act
        var offerId = offerFacade.registerOffer(registerDto);
        var actual = offerFacade.getOfferWithId(offerId);

        // Validate
        assertThat(actual).isNotNull();
    }

    @Test
    public void removeOffer(){
        // Setup
        var userDto = getUserDTO();
        var registerDto = getRegisterOffer(userDto);
        var offerId = offerFacade.registerOffer(registerDto);

        // Act
        offerFacade.removeOffer(offerId);
    }

    @Test
    public void changeDescription(){
        // Setup
        var newDesc = "changed";
        var newTitle = "Changed";

        var userDto = getUserDTO();
        var registerDto = getRegisterOffer(userDto);
        var offerId = offerFacade.registerOffer(registerDto);

        var descDto = new ChangeDescriptionOfferDTO();
        descDto.setDescription(newDesc);
        descDto.setTitle(newTitle);
        descDto.setId(offerId);

        // Act
        offerFacade.changeDescription(descDto);

        // Validate
        var actual = offerFacade.getOfferWithId(offerId);

        assertThat(actual.getDescription()).isEqualTo(newDesc);
        assertThat(actual.getTitle()).isEqualTo(newTitle);
    }

    private RegisterOfferDTO getRegisterOffer(UserDTO userDTO){
        var offer = new RegisterOfferDTO();

        offer.setCapacity(10);
        offer.setEvents(new ArrayList<>());
        offer.setPrice(new BigDecimal(110));
        offer.setDescription("Register");
        offer.setTitle("Title");
        offer.setOwner(userDTO);

        return offer;
    }

    private UserDTO getUserDTO(){
        RegisterUserDTO userDto = new RegisterUserDTO();
        userDto.setUsername("testUser");
        userDto.setPassword("Argon2");
        userDto.setName("John");
        userDto.setSurname("Doe");
        userDto.setPhoneNumber("+420123456789");
        userDto.setEmail("a@a.cz");

        long userId = userFacade.registerUser(userDto);
        var dto = userFacade.getById(userId);
        dto.setId(userId);
        return dto;
    }
}
