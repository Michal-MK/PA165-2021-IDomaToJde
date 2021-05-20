package cz.idomatojde.facade.base;

import java.util.List;

/**
 * Base facade responsible for CRUD operations
 *
 * @author Michal Hazdra
 */
public interface BaseFacade<TRegDto, TDto> {

    /**
     * Register a new object into he system
     *
     * @param dto the registration DTO defining the object
     * @return the unique ID of the newly created object
     */
    long register(TRegDto dto);

    /**
     * Obtain all instances of defined object type
     *
     * @return a collection of all instances
     */
    List<TDto> getAll();

    /**
     * Obtain an object stored in the system
     *
     * @param id the unique ID of the object
     * @return the DTO instance of the object
     */
    TDto getById(long id);

    /**
     * Delete an object from the system
     *
     * @param obj the concrete object
     */
    void delete(TDto obj);

    /**
     * Delete an object from the system
     *
     * @param id the unique ID of an object
     */
    void delete(long id);
}
