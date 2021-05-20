package cz.idomatojde.services.facades.base;

import cz.idomatojde.entity.base.IEntity;
import cz.idomatojde.facade.base.BaseFacade;
import cz.idomatojde.services.base.BaseService;
import cz.idomatojde.services.base.MappingService;

import java.util.List;
import java.util.stream.Collectors;

public class BaseFacadeImpl<TRegDto, TDto, TEntity extends IEntity> implements BaseFacade<TRegDto, TDto> {

    private final BaseService<TEntity> baseService;
    protected final MappingService mapService;
    private final Class<TDto> dtoCls;
    private final Class<TEntity> entityCls;

    public BaseFacadeImpl(BaseService<TEntity> baseSvc, MappingService map, Class<TDto> dtoCls, Class<TEntity> entityCls) {
        baseService = baseSvc;
        mapService = map;
        this.dtoCls = dtoCls;
        this.entityCls = entityCls;
    }

    @Override
    public long register(TRegDto regDto) {
        TEntity entity = mapService.mapDto(regDto, entityCls);
        return baseService.create(entity);
    }

    @Override
    public List<TDto> getAll() {
        return baseService.findAll().stream()
                .map(m -> mapService.mapEntity(m, dtoCls))
                .collect(Collectors.toList());
    }

    @Override
    public TDto getById(long id) {
        return mapService.mapEntity(baseService.getById(id), dtoCls);
    }

    @Override
    public void delete(TDto obj) {
        baseService.delete(mapService.mapDto(obj, entityCls));
    }

    @Override
    public void delete(long id) {
        baseService.delete(id);
    }
}
