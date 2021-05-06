package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.Category;

/**
 * API for {@link Category} Entities
 *
 * @author Michal Hazdra
 */
public interface CategoryDao extends BaseDAO<Category> {
    Category getByName(String name);
}
