package cz.idomatojde.services.base;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.base.IEntity;

import java.util.List;

public abstract class BaseServiceImpl<TEntity extends IEntity> implements BaseService<TEntity> {

    final BaseDAO<TEntity> base;

    public BaseServiceImpl(BaseDAO<TEntity> base) {
        this.base = base;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long create(TEntity entity) {
        base.create(entity);
        return entity.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TEntity> findAll() {
        return base.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TEntity getById(Long id) {
        return base.getById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(TEntity entity) {
        base.delete(entity);
    }
}
