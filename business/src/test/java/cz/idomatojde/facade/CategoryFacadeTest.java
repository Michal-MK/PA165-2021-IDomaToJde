package cz.idomatojde.facade;

import cz.idomatojde.dto.category.CategoryDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CategoryFacadeTest extends AbstractTestNGSpringContextTests {

    private static final String CAT_NAME = "Exercise";

    @Inject
    CategoryFacade categoryFacade;

    @Test
    void categoryIntegration() {
        CategoryDTO c = new CategoryDTO();
        c.setName(CAT_NAME);

        long id = categoryFacade.register(c);
        c.setId(id);

        CategoryDTO retrieved = categoryFacade.getByName(CAT_NAME);

        assertThat(retrieved.getName()).isEqualTo(CAT_NAME);

        CategoryDTO nonexistent = categoryFacade.getByName("CAT_NAME");
        assertThat(nonexistent).isNull();

        categoryFacade.delete(retrieved.getId());

        c.setId(null);
        categoryFacade.register(c);

        retrieved = categoryFacade.getByName(CAT_NAME);
        categoryFacade.delete(retrieved);
    }
}
