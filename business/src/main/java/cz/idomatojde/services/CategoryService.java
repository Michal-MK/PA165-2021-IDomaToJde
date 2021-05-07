package cz.idomatojde.services;

import cz.idomatojde.entity.Category;
import cz.idomatojde.services.base.BaseService;

/**
 * Service class for {@link Category}
 *
 * @author Michal Hazdra
 */
public interface CategoryService extends BaseService<Category> {

    /**
     * Creates a new Category
     * @param name the unique name of the category
     * @return the ID of the newly created entity
     */
    long registerCategory(String name);

    /**
     * Function to obtain the {@link Category} given its name
     * @param name the name of the {@link Category}
     * @return the found {@link Category}
     */
    Category getByName(String name);
}
