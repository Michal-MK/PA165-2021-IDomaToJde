package cz.idomatojde.services.facades;

import cz.idomatojde.configuration.CustomMapper;
import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.facade.CategoryFacade;
import cz.idomatojde.services.CategoryService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author Michal Hazdra
 */
@Service
@Transactional
public class CategoryFacadeImpl implements CategoryFacade {

    private final CategoryService categoryService;

    @Inject
    public CategoryFacadeImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public long registerCategory(CategoryDTO categoryDTO) {
        return categoryService.registerCategory(categoryDTO.getName());
    }

    @Override
    public CategoryDTO getByName(String name) {
        return CustomMapper.toCategoryDTO(categoryService.getByName(name));
    }
}
