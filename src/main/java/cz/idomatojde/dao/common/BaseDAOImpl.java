package cz.idomatojde.dao.common;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Michal Hazdra
 * @param <TEntity> Any entity
 */
@Repository
public abstract class BaseDAOImpl<TEntity> implements BaseDAO<TEntity> {

    private final Class<TEntity> cls;

    @PersistenceContext
    public EntityManager em;

    public BaseDAOImpl(Class<TEntity> cls) {
        this.cls = cls;
    }

    @Transactional
    public void create(TEntity entity) {
        em.persist(entity);
    }

    public List<TEntity> getAll() {
        return em.createQuery("select a from " + cls.getName() + " a", cls).getResultList();
    }


    public TEntity getById(Long id) {
        return em.createQuery("select a from " + cls.getName() + " a where a.id = :id", cls)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public abstract void update(TEntity entity);

    @Transactional
    public void delete(TEntity entity) {
        em.remove(entity);
    }
}
