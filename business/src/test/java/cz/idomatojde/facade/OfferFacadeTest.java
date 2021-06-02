package cz.idomatojde.facade;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
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
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jiri Vrbka
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class OfferFacadeTest extends AbstractTestNGSpringContextTests {

    @Inject
    private UserFacade userFacade;

    @Inject
    private OfferFacade offerFacade;

    @Inject
    private CategoryFacade categoryFacade;

    @Test
    public void registerOfferAndGetOfferWithId() {
        // Setup
        var userDto = getUserDTO();
        var categoryDto = getCategory();
        var registerDto = getRegisterOffer(userDto, categoryDto);

        registerDto.setCategoryId(categoryDto.getId());

        // Act
        var offerId = offerFacade.register(registerDto);
        var actual = offerFacade.getById(offerId);

        // Validate
        assertThat(actual).isNotNull();
    }

    @Test
    public void removeOffer() {
        // Setup
        var userDto = getUserDTO();
        var categoryDto = getCategory();
        var registerDto = getRegisterOffer(userDto, categoryDto);

        registerDto.setCategoryId(categoryDto.getId());
        var offerId = offerFacade.register(registerDto);

        // Act
        offerFacade.delete(offerId);
    }

    @Test
    public void changeDescription() {
        // Setup
        var newDesc = "changed";
        var newTitle = "Changed";

        var userDto = getUserDTO();
        var categoryDto = getCategory();
        var registerDto = getRegisterOffer(userDto, categoryDto);

        registerDto.setCategoryId(categoryDto.getId());

        var offerId = offerFacade.register(registerDto);

        var descDto = new ChangeDescriptionOfferDTO();
        descDto.setDescription(newDesc);
        descDto.setTitle(newTitle);
        descDto.setId(offerId);

        // Act
        offerFacade.changeDescription(descDto);

        // Validate
        var actual = offerFacade.getById(offerId);

        assertThat(actual.getDescription()).isEqualTo(newDesc);
        assertThat(actual.getTitle()).isEqualTo(newTitle);
    }

    @Test
    void paginationTest() {
        var cat = getCategory();
        var user = getUserDTO();

        for (int i = 0; i < 50; i++) {
            RegisterOfferDTO reg = getRegisterOffer(user, cat);
            reg.setTitle("" + i);
            offerFacade.register(reg);
        }

        var page1 = offerFacade.getPaged(1, 10);

        assertThat(page1.size()).isEqualTo(10);
        assertThat(page1.stream().map(OfferDTO::getId).collect(Collectors.toList()))
                .containsAll(LongStream.range(1, 11).boxed().collect(Collectors.toList()));

        var page2 = offerFacade.getPaged(2, 20);

        assertThat(page2.size()).isEqualTo(20);
        assertThat(page2.stream().map(OfferDTO::getId).collect(Collectors.toList()))
                .containsAll(LongStream.range(21, 41).boxed().collect(Collectors.toList()));
    }

    private CategoryDTO getCategory() {
        var category = new CategoryDTO();
        category.setName("Category");
        long id = categoryFacade.register(category);
        category.setId(id);
        return category;
    }

    private RegisterOfferDTO getRegisterOffer(UserDTO userDTO, CategoryDTO categoryDTO) {
        var offer = new RegisterOfferDTO();

        offer.setCapacity(10);
        offer.setPrice(new BigDecimal(110));
        offer.setDescription("Register");
        offer.setTitle("Title");
        offer.setOwnerId(userDTO.getId());
        offer.setCategoryId(categoryDTO.getId());

        return offer;
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
        var dto = userFacade.getById(userId);
        dto.setId(userId);
        return dto;
    }
}
