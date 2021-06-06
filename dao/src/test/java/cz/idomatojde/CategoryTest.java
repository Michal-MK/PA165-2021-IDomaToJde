package cz.idomatojde;

import cz.idomatojde.dao.CategoryDao;
import cz.idomatojde.dao.OfferDao;
import cz.idomatojde.dao.UserDao;
import cz.idomatojde.entity.Category;
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

/**
 * Category specific DAO tests
 *
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@DataJpaTest
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryTest extends AbstractTestNGSpringContextTests {

    @Inject
    public OfferDao offerDao;

    @Inject
    public UserDao userDao;

    @Inject
    public CategoryDao catDao;


    @Test
    void getByName() {
        //Setup
        Category category = new Category();
        category.setName("TestName");
        catDao.create(category);

        //Act
        Category retrieved = catDao.getByName("TestName");

        //Validate
        assertThat(retrieved).isEqualTo(category);
    }
}
