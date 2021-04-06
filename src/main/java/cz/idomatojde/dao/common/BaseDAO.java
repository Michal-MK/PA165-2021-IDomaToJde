package cz.idomatojde.dao.common;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Michal Hazdra
 * @param <TEntity> Any entity
 */
public interface BaseDAO<TEntity> {

    @Transactional
    void create(TEntity entity);

    List<TEntity> getAll();

    TEntity getById(Long id);

    @Transactional
    void update(TEntity entity);

    @Transactional
    void delete(TEntity entity);
}
