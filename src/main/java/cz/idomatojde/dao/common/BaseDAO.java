package cz.idomatojde.dao.common;

import javax.transaction.Transactional;
import java.util.List;

/** Base interface for all DAOs
 * @author Michal Hazdra
 * @param <TEntity> Any entity
 */
public interface BaseDAO<TEntity> {


    /** Common functionality to persist a new entity
     * @param entity the entity to persist
     */
    void create(TEntity entity);


    /** Common functionality to find all entities
     * @return the list of all entities in the database
     */
    List<TEntity> findAll();


    /** Common functionality to obtain an entity by its id
     * @param id the entity identifier
     * @return the entity
     */
    TEntity getById(Long id);


    /** Common functionality to update the given entity
     * @param entity the entity to update within the database
     */
    void update(TEntity entity);


    /** Common functionality to delete an entity from the database
     * @param entity the entity to delete
     */
    void delete(TEntity entity);
}
