package cz.idomatojde;

import cz.idomatojde.dao.CategoryDao;
import cz.idomatojde.entity.Category;
import cz.idomatojde.services.CategoryService;
import cz.idomatojde.services.CategoryServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Michal Hazdra
 */
public class CategoryTest {
    private CategoryDao mockDao;

    private CategoryService service;

    private static final String NAME = "Exercise";

    @BeforeMethod
    public void setUp() {
        mockDao = mock(CategoryDao.class);
        service = new CategoryServiceImpl(mockDao);

        when(mockDao.getByName(NAME)).thenAnswer((a) -> {
            Category c = new Category();
            c.setName(NAME);
            return c;
        });
    }

    @Test
    void getByName() {
        Category category = service.getByName(NAME);

        assertThat(category.getName()).isEqualTo(NAME);
    }


    @Test
    void getByNameNonExistent() {
        Category category = service.getByName("NonExistent");

        assertThat(category).isNull();
    }

    @Test
    void delete() {
        service.delete(new Category());

        verify(mockDao,times(1)).delete(new Category());
    }
}
