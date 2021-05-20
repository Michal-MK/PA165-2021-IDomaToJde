package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAOImpl;
import cz.idomatojde.entity.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;


/**
 * DAO implementation for {@link CategoryDao} API
 *
 * @author Michal Hazdra
 */
@Repository
public class CategoryDaoImpl extends BaseDAOImpl<Category> implements CategoryDao {

    public CategoryDaoImpl() {
        super(Category.class);
    }

    @Override
    public Category getByName(String name) {
        try {
            return em.createQuery("select c from Category c where c.name = :name", Category.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
