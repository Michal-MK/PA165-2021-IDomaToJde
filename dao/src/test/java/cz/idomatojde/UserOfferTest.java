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
import org.testng.annotations.Test;

import javax.inject.Inject;

import static cz.idomatojde.TestObjects.getUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import static cz.idomatojde.TestObjects.getOffer;

/**
 * User x Offer DAO tests
 *
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@DataJpaTest
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserOfferTest extends AbstractTestNGSpringContextTests {

    @Inject
    private UserDao userDao;

    @Inject
    private OfferDao offerDao;

    @Inject
    private CategoryDao catDao;

    private Category category;

    @BeforeMethod
    public void setUp() {
        category = new Category();
        category.setName("Category");
        catDao.create(category);
    }

    @Test
    public void userSubscribedOffers() {
        //Setup
        User user = getUser("Pepe");
        userDao.create(user);

        Offer offer = getOffer(category, "Java");
        offer.setOwner(user);

        offerDao.create(offer);

        User u2 = getUser("John");
        long id = userDao.create(u2);

        u2.setSubscribedOffers(List.of(offer));

        //Act
        User updated = userDao.getById(id);

        //Validate
        assertThat(updated.getSubscribedOffers().size()).isEqualTo(1);
        assertThat(updated.getSubscribedOffers().get(0).getTitle()).isEqualTo("Java");
    }

    @Test
    public void offerSubscribers() {
        //Setup
        User u = getUser("Pepe");
        userDao.create(u);

        User u2 = getUser("John");
        userDao.create(u2);

        Offer offer = getOffer(category, "Offer");
        offer.setOwner(u);
        offer.getSubscribers().add(u2);

        offerDao.create(offer);

        //Act
        Offer updated = offerDao.getById(1L);

        //Validate
        assertThat(updated.getSubscribers().size()).isEqualTo(1);
        assertThat(updated.getSubscribers().get(0).getUsername()).isEqualTo("John");
    }
}
