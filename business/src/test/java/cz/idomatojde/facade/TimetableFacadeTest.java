package cz.idomatojde.facade;

import cz.idomatojde.dto.base.DurationDTO;
import cz.idomatojde.dto.base.LocalTimeDTO;
import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class TimetableFacadeTest extends AbstractTestNGSpringContextTests {

    @Inject
    TimetableFacade timetableFacade;

    @Inject
    UserFacade userFacade;

    @Inject
    CategoryFacade categoryFacade;

    @Inject
    OfferFacade offerFacade;

    private long setUp() {
        RegisterUserDTO userDto = new RegisterUserDTO();
        userDto.setUsername("testUser");
        userDto.setPassword("hashhhhh");
        userDto.setName("John");
        userDto.setSurname("Doe");
        userDto.setPhoneNumber("+420123456789");
        userDto.setEmail("a@a.cz");

        long userId = userFacade.register(userDto);

        AddTimetableDTO dto = new AddTimetableDTO();
        dto.setUserId(userId);
        dto.setYear(2030);
        dto.setWeek(40);

        return timetableFacade.register(dto);
    }

    @Test
    void integrationTestUserAndTheirEmail() {
        long id = setUp();

        TimetableDTO timetableDTO = timetableFacade.getById(id);

        assertThat(timetableDTO.getYear()).isEqualTo(2030);
        assertThat(timetableDTO.getUserInfo().getEmail()).isEqualTo("a@a.cz");
    }

    @Test
    void integrationTestAllEntries() {
        long id = setUp();

        CategoryDTO cat = new CategoryDTO();
        cat.setName("Exercise");

        cat.setId(categoryFacade.register(cat));

        RegisterOfferDTO offer = new RegisterOfferDTO();
        offer.setTitle("Title");
        offer.setPrice(BigDecimal.ONE);
        offer.setCategory(cat);
        offer.setCapacity(20);
        offer.setOwner(userFacade.getById(1));
        offer.setDescription("Description");

        long offerId = offerFacade.register(offer);

        CreateTimetableEntryDTO entry = new CreateTimetableEntryDTO();

        entry.setTimetable(timetableFacade.getById(id));

        LocalTimeDTO time = new LocalTimeDTO();
        time.setHour(20);
        time.setMinute(30);
        time.setSecond(0);

        entry.setEntryStart(time);
        entry.setDescription("Description");
        entry.setDay(10);

        DurationDTO duration = new DurationDTO();
        duration.setMinutes(120);

        entry.setLength(duration);
        entry.setOffer(offerFacade.getById(offerId));

        long entryId = timetableFacade.registerEntry(entry);

        TimetableEntryDTO retrievedEntry = timetableFacade.getEntryById(entryId);

        TimetableDTO full = timetableFacade.getWithEntries(id);

        assertThat(full.getId()).isEqualTo(id);
        assertThat(full.getEntries().get(0).getId()).isEqualTo(retrievedEntry.getId());
        assertThat(full.getEntries().get(0).getEntryStart()).isEqualTo(retrievedEntry.getEntryStart());
        assertThat(full.getEntries().get(0).getDay()).isEqualTo(retrievedEntry.getDay());
        assertThat(full.getEntries().get(0).getLength()).isEqualTo(retrievedEntry.getLength());
    }
}
