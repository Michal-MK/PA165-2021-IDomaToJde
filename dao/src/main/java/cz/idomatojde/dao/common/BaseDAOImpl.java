package cz.idomatojde.dao.common;

import cz.idomatojde.entity.base.IEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Base class for all DAOs
 *
 * @param <TEntity> Any entity
 * @author Michal Hazdra
 */
public abstract class BaseDAOImpl<TEntity extends IEntity> implements BaseDAO<TEntity> {

    private final Class<TEntity> cls;

    @PersistenceContext
    protected EntityManager em;

    /**
     * Constructs the base type with the necessary information
     *
     * @param cls the Class instance for reflection
     */
    public BaseDAOImpl(Class<TEntity> cls) {
        this.cls = cls;
    }

    @Override
    public long create(TEntity entity) {
        em.persist(entity);
        return entity.getId();
    }

    @Override
    public List<TEntity> findAll() {
        return em.createQuery("select a from " + cls.getName() + " a", cls).getResultList();
    }

    @Override
    public List<TEntity> findPaged(int page, int size) {
        return em.createQuery("select a from " + cls.getName() + " a", cls)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public TEntity getById(long id) {
        return em.createQuery("select a from " + cls.getName() + " a where a.id = :id", cls)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public TEntity merge(TEntity entity) {
        return em.merge(entity);
    }

    @Override
    public void delete(TEntity entity) {
        TEntity merged = merge(entity);
        em.remove(merged);
    }

    @Override
    public void delete(long id) {
        em.createQuery("delete from " + cls.getName() + " a where a.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
