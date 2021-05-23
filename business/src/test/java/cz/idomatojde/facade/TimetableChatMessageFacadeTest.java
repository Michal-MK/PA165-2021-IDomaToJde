package cz.idomatojde.facade;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.services.base.MappingService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jiri Vrbka
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TimetableChatMessageFacadeTest extends AbstractTestNGSpringContextTests {
    @Inject
    private TimetableFacade timetableFacade;

    @Inject
    private UserFacade userFacade;

    @Inject
    private OfferFacade offerFacade;

    @Inject
    private CategoryFacade categoryFacade;

    @Inject
    private TimetableChatMessageFacade chatMessageFacade;

    @Inject
    private MappingService mappingService;

    @Test
    public void addTimetableChatMessage() {
        // Setup
        var userDto = getUserDTO();
        var offerDto = getOfferDTO(userDto);
        var entryDto = getTimetableEntryDTO(userDto, offerDto);

        var dto = new AddTimetableChatMessageDTO();
        dto.setText("text");
        dto.setSenderId(userDto.getId());
        dto.setTimetableEntryId(entryDto.getId());

        // Act
        var msgId = chatMessageFacade.register(dto);

        // Validate
        var actual = chatMessageFacade.getById(msgId);

        assertThat(actual).isNotNull();
    }

    @Test
    public void changeText() {
        // Setup
        var msgDto = getTimetableChatMessageDTO();
        var change = new ChangeTextTimetableChatMessageDTO();
        change.setText("new");
        change.setId(msgDto.getId());

        // Act
        chatMessageFacade.changeText(change);

        // Validate
        var actual = chatMessageFacade.getById(msgDto.getId());

        assertThat(actual.getText()).isEqualTo(change.getText());
    }

    private OfferDTO getOfferDTO(UserDTO userDTO) {
        var category = new CategoryDTO();
        category.setName("Category");
        var catId = categoryFacade.register(category);
        category.setId(catId);

        var offer = new RegisterOfferDTO();

        offer.setCapacity(10);
        offer.setPrice(new BigDecimal(110));
        offer.setDescription("Register");
        offer.setTitle("Title");
        offer.setOwnerId(userDTO.getId());
        offer.setCategoryId(category.getId());
        offer.setCategoryId(catId);

        var offerId = offerFacade.register(offer);
        return offerFacade.getById(offerId);
    }

    private UserDTO getUserDTO() {
        RegisterUserDTO userDto = new RegisterUserDTO();
        userDto.setUsername("testUser");
        userDto.setPassword("Argon2");
        userDto.setName("John");
        userDto.setSurname("Doe");
        userDto.setPhoneNumber("+420123456789");
        userDto.setEmail("a@a.cz");

        long userId = userFacade.register(userDto);
        return userFacade.getById(userId);
    }

    private TimetableEntryDTO getTimetableEntryDTO(UserDTO userDto, OfferDTO offerDTO) {
        AddTimetableDTO dto = new AddTimetableDTO();
        dto.setUserId(userDto.getId());
        dto.setYear(2030);
        dto.setWeek(40);
        long id = timetableFacade.register(dto);
        TimetableDTO timetableDTO = timetableFacade.getById(id);

        var entryDto = new CreateTimetableEntryDTO();
        entryDto.setOfferId(offerDTO.getId());
        entryDto.setTimetableId(timetableDTO.getId());
        entryDto.setLength(mappingService.toDurationDTO(Duration.ofHours(1)));
        entryDto.setEntryStart(mappingService.toLocalTimeDTO(LocalTime.MIDNIGHT));

        var entryId = timetableFacade.registerEntry(entryDto);
        return timetableFacade.getEntryById(entryId);
    }

    private TimetableChatMessageDTO getTimetableChatMessageDTO() {
        var userDto = getUserDTO();
        var offerDto = getOfferDTO(userDto);
        var entryDto = getTimetableEntryDTO(userDto, offerDto);

        var dto = new AddTimetableChatMessageDTO();
        dto.setText("text");
        dto.setSenderId(userDto.getId());
        dto.setTimetableEntryId(entryDto.getId());

        var msgId = chatMessageFacade.register(dto);

        return chatMessageFacade.getById(msgId);
    }
}
