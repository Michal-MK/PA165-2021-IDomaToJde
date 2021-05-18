package cz.idomatojde.services.facades;

import cz.idomatojde.services.base.MappingService;
import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.entity.Category;
import cz.idomatojde.facade.CategoryFacade;
import cz.idomatojde.services.CategoryService;
import cz.idomatojde.services.facades.base.BaseFacadeImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author Michal Hazdra
 */
@Service
@Transactional
public class CategoryFacadeImpl extends BaseFacadeImpl<CategoryDTO, CategoryDTO, Category> implements CategoryFacade {

    private final CategoryService categoryService;

    @Inject
    public CategoryFacadeImpl(CategoryService categoryService, MappingService map) {
        super(categoryService, map, CategoryDTO.class, Category.class);
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDTO getByName(String name) {
        return mapService.toCategoryDTO(categoryService.getByName(name));
    }
}
