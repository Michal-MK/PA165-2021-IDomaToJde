package cz.idomatojde.services.base;

import cz.idomatojde.dao.common.BaseDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class BaseServiceImpl<TEntity> implements BaseService<TEntity> {

    final BaseDAO<TEntity> base;

    public BaseServiceImpl(BaseDAO<TEntity> base) {
        this.base = base;
    }

    @Override
    public void create(TEntity entity) {
        base.create(entity);
    }

    @Override
    public List<TEntity> findAll() {
        return base.findAll();
    }

    @Override
    public TEntity getById(Long id) {
        return base.getById(id);
    }

    @Override
    public void delete(TEntity entity) {
        base.delete(entity);
    }
}
