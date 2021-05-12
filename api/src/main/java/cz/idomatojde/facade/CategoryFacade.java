package cz.idomatojde.facade;

import cz.idomatojde.dto.category.CategoryDTO;

/**
 * Facade responsible for all things concerning categories
 *
 * @author Michal Hazdra
 */
public interface CategoryFacade {

    /**
     * Create a new category with the specified name
     *
     * @param categoryDTO the DTO of the category
     * @return the ID of the newly created entity
     */
    long registerCategory(CategoryDTO categoryDTO);

    /**
     * Retrieve a category by its name
     * @param name the unique name of the category
     * @return the information about the category
     */
    CategoryDTO getByName(String name);
}
