package cz.idomatojde.facade;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.facade.base.BaseFacade;

/**
 * Facade responsible for all things concerning categories
 *
 * @author Michal Hazdra
 */
public interface CategoryFacade extends BaseFacade<CategoryDTO, CategoryDTO> {

    /**
     * Retrieve a category by its name
     * @param name the unique name of the category
     * @return the information about the category
     */
    CategoryDTO getByName(String name);
}
