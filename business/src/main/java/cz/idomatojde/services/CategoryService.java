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
     * Function to obtain the {@link Category} given its name
     * @param name the name of the {@link Category}
     * @return the found {@link Category}
     */
    Category getByName(String name);
}
