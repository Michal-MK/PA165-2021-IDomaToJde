package cz.idomatojde.services.base;

import cz.idomatojde.entity.base.IEntity;

import java.util.List;

/**
 * Base service interface containing CRUD functionality
 *
 * @param <TEntity> the backing Entity class this instance services
 * @author Michal Hazdra
 */
public interface BaseService<TEntity extends IEntity> {

    /**
     * <b>Create</b> operation for storing a new entity
     *
     * @param entity the entity to create at the repository layer
     * @return the ID of the newly created entity
     */
    long create(TEntity entity);

    /**
     * <b>Retrieve</b> operation for obtaining a list of all entities of class TEntity
     *
     * @return the list of all entities
     */
    List<TEntity> findAll();

    /**
     * <b>Retrieve</b> operation for obtaining a single entity by its ID
     *
     * @param id the unique identifier of the entity
     * @return the entity identified by the id
     */
    TEntity getById(long id);

    /**
     * <b>Delete</b> operation for removing the entity from the repository
     *
     * @param entity the entity to delete
     */
    void delete(TEntity entity);

    /**
     * <b>Delete</b> operation for removing the entity from the repository
     *
     * @param entityId the identifier of an entity
     */
    void delete(long entityId);
}
