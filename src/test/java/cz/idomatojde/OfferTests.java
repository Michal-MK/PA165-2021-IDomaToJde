package cz.idomatojde;

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
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Offer specific DAO tests
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

    public Offer getOffer(String title) {
        Offer offer = new Offer();
        offer.setTitle(title);
        offer.setOwner(UserTests.getUser("user"));
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
    public void offerCreation() {
        //Setup
        final String name = "Java";
        Offer offer = getOffer(name);

        //Act
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        //Validate
        assertThat(offer.getId()).isEqualTo(1L);
        assertThat(offerDao.getById(1L).getTitle()).isEqualTo(name);
        assertThat(offerDao.getById(1L).getOwner()).isEqualTo(UserTests.getUser("user"));
        assertThat(offerDao.getById(1L).getDescription()).isEqualTo("description");
        assertThat(offerDao.getById(1L).getCategory()).isEqualTo(Category.EDUCATION);
        assertThat(offerDao.getById(1L).getCapacity()).isEqualTo(10);
        assertThat(offerDao.getById(1L).getRegistered()).isEqualTo(5);
        assertThat(offerDao.getById(1L).getPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(offerDao.getById(1L).getCreatedDate()).isEqualTo(LocalDate.of(2021, 4, 1));
        assertThat(offerDao.getById(1L).getExpirationDate()).isEqualTo(LocalDate.of(2021,4,20));
    }

    @Test
    public void offerFindByUserEmpty() {
        //Setup
        final String title = "Offer";
        Offer offer = getOffer(title);
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
        Offer offer = getOffer(title);
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
        Offer offer = getOffer(title);

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
        Offer offer = getOffer(title);
        LocalDate newDate = LocalDate.of(2022,1,1);
        //Act
        userDao.create(offer.getOwner());
        offerDao.create(offer);

        offer.setPrice(BigDecimal.TEN);
        offer.setCapacity(666);
        offer.setExpirationDate(newDate);

        offerDao.update(offer);
        Offer updated = offerDao.getById(1L);

        //Validate
        assertThat(updated.getTitle()).isEqualTo(title);
        assertThat(updated.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(updated.getCapacity()).isEqualTo(666);
        assertThat(updated.getExpirationDate()).isEqualTo(newDate);
    }
}
