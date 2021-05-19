package cz.idomatojde.services;

import cz.idomatojde.dao.CategoryDao;
import cz.idomatojde.entity.Category;
import cz.idomatojde.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Michal Hazdra
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    private final CategoryDao categories;

    @Inject
    public CategoryServiceImpl(CategoryDao categories) {
        super(categories);
        this.categories = categories;
    }

    @Override
    public Category getByName(String name) {
        return categories.getByName(name);
    }
}
