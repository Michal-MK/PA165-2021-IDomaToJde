package cz.idomatojde.services.base;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.base.IEntity;

import java.util.List;

/**
 * @param <TEntity> the entity class serviced
 * @author Michal Hazdra
 */
public abstract class BaseServiceImpl<TEntity extends IEntity> implements BaseService<TEntity> {

    final BaseDAO<TEntity> base;

    public BaseServiceImpl(BaseDAO<TEntity> base) {
        this.base = base;
    }

    @Override
    public long create(TEntity entity) {
        base.create(entity);
        return entity.getId();
    }

    @Override
    public List<TEntity> findAll() {
        return base.findAll();
    }

    @Override
    public List<TEntity> findPaged(int page, int size) {
        return base.findPaged(page, size);
    }

    @Override
    public TEntity getById(long id) {
        return base.getById(id);
    }

    @Override
    public void delete(TEntity entity) {
        base.delete(entity);
    }

    @Override
    public void delete(long entityId) {
        base.delete(entityId);
    }
}
