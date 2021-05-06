package cz.idomatojde;

import cz.idomatojde.dao.CategoryDao;
import cz.idomatojde.dao.OfferDao;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static cz.idomatojde.TestObjects.getOffer;
import static cz.idomatojde.TestObjects.getUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Offer specific DAO tests
 *
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@DataJpaTest
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OfferTests extends AbstractTestNGSpringContextTests {

    @Inject
    public OfferDao offerDao;

    @Inject
    public UserDao userDao;

    @Inject
    public CategoryDao catDao;

    private Category category;

    @BeforeMethod
    void setup() {
        category = new Category();
        category.setName("Category");
        catDao.create(category);
    }

    @Test
    public void offerCreation() {
        //Setup
        final String name = "Java";

        Offer offer = getOffer(category, name);

        //Act
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        //Validate
        assertThat(offer.getId()).isEqualTo(1L);
        assertThat(offerDao.getById(1L).getTitle()).isEqualTo(name);
        assertThat(offerDao.getById(1L).getOwner()).isEqualTo(offer.getOwner());
        assertThat(offerDao.getById(1L).getDescription()).isEqualTo("description");
        assertThat(offerDao.getById(1L).getCategory()).isEqualTo(category);
        assertThat(offerDao.getById(1L).getCapacity()).isEqualTo(10);
        assertThat(offerDao.getById(1L).getRegistered()).isEqualTo(5);
        assertThat(offerDao.getById(1L).getPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(offerDao.getById(1L).getCreatedDate()).isEqualTo(LocalDate.of(2021, 4, 1));
        assertThat(offerDao.getById(1L).getExpirationDate()).isEqualTo(LocalDate.now().plusDays(1));
    }

    @Test
    public void offerFindByUserEmpty() {
        //Setup
        final String title = "Offer";
        Offer offer = getOffer(category, title);
        User user = offer.getOwner();

        //Act
        userDao.create(user);

        //Validate
        assertThat(offerDao.findByUser(user)).isNotNull();
        assertThat(offerDao.findByUser(user).size()).isEqualTo(0);
    }

    @Test
    public void offerFindByUser() {
        //Setup
        final String title = "Offer";
        Offer offer = getOffer(category, title);
        User user = offer.getOwner();

        //Act
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        //Validate
        assertThat(offerDao.getById(1L).getOwner()).isEqualTo(user);
        assertThat(offerDao.findByUser(user)).isNotNull();
        assertThat(offerDao.findByUser(user).size()).isEqualTo(1);
        assertThat(offerDao.findByUser(user).get(0).getTitle()).isEqualTo(title);
    }

    @Test
    public void emptyOffers() {
        //Act
        assertThat(offerDao.getActiveOffers()).isNotNull();
        assertThat(offerDao.getActiveOffers().size()).isEqualTo(0);
    }

    @Test
    public void someOffers() {
        //Setup
        final String title = "Offer";
        Offer offer = getOffer(category, title);

        //Act
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        //Validate
        assertThat(offerDao.getActiveOffers()).isNotNull();
        assertThat(offerDao.getActiveOffers().size()).isEqualTo(1);
        assertThat(offerDao.getActiveOffers().get(0).getTitle()).isEqualTo(title);
    }

    @Test
    public void offerUpdate() {
        //Setup
        final String title = "Offer";
        Offer offer = getOffer(category, title);
        LocalDate newDate = LocalDate.of(2022, 1, 1);
        //Act
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        offer.setPrice(BigDecimal.TEN);
        offer.setCapacity(666);
        offer.setExpirationDate(newDate);

        Offer updated = offerDao.getById(1L);

        //Validate
        assertThat(updated.getTitle()).isEqualTo(title);
        assertThat(updated.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(updated.getCapacity()).isEqualTo(666);
        assertThat(updated.getExpirationDate()).isEqualTo(newDate);
    }

    @Test
    public void getOffersByCategory() {
        //Setup
        final String title = "Offer";
        Offer offer = getOffer(category, title);
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        //Act
        List<Offer> retrieved = offerDao.getAllByCategory(category);
        Offer only = retrieved.get(0);

        //Validate
        assertThat(only.getTitle()).isEqualTo(title);
        assertThat(only.getCategory().getName()).isEqualTo("Category");
    }


    @Test
    public void getSubscribedOffersEmpty() {
        //Setup
        final String title = "Offer";
        Offer offer = getOffer(category, title);
        User other = getUser("otherUser");

        //Act
        userDao.create(offer.getOwner());
        userDao.create(other);
        offerDao.create(offer);

        List<Offer> retrieved = offerDao.getSubscribedOffers(other);

        //Validate
        assertThat(retrieved.size()).isEqualTo(0);
    }

    @Test
    public void getSubscribedOffers() {
        //Setup
        Offer offer = getOffer(category, "Offer");
        Offer offer2 = getOffer(category, "otherOffer");
        User other = getUser("otherUser");
        User incomingUser = getUser("otherUser");
        userDao.create(offer.getOwner());
        userDao.create(offer2.getOwner());
        userDao.create(other);
        offerDao.create(offer);
        offerDao.create(offer2);

        incomingUser.setId(other.getId());

        //Act
        other.setSubscribedOffers(List.of(offer2));

        List<Offer> retrieved = offerDao.getSubscribedOffers(incomingUser);

        //Validate
        assertThat(retrieved.size()).isEqualTo(1);
        assertThat(retrieved.get(0)).isEqualTo(offer2);
    }
}
