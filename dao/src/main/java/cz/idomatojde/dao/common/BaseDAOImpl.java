package cz.idomatojde.dao.common;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/** Base class for all DAOs
 * @author Michal Hazdra
 * @param <TEntity> Any entity
 */
@Repository
public abstract class BaseDAOImpl<TEntity> implements BaseDAO<TEntity> {

    private final Class<TEntity> cls;

    @PersistenceContext
    protected EntityManager em;


    /** Constructs the base type with the necessary information
     * @param cls the Class instance for reflection
     */
    public BaseDAOImpl(Class<TEntity> cls) {
        this.cls = cls;
    }

    public void create(TEntity entity) {
        em.persist(entity);
    }

    public List<TEntity> findAll() {
        return em.createQuery("select a from " + cls.getName() + " a", cls).getResultList();
    }


    public TEntity getById(Long id) {
        return em.createQuery("select a from " + cls.getName() + " a where a.id = :id", cls)
                .setParameter("id", id)
                .getSingleResult();
    }


    /** The function to override to handle updates of this entity
     * @param entity the entity to update within the database
     */
    public abstract void update(TEntity entity);

    public void delete(TEntity entity) {
        em.remove(entity);
    }
}
