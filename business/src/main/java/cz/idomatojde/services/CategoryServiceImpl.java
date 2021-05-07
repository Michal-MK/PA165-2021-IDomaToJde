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
    public long registerCategory(String name) {
        var cat = new Category();
        cat.setName(name);
        return categories.create(cat);
    }

    @Override
    public Category getByName(String name) {
        return categories.getByName(name);
    }
}
