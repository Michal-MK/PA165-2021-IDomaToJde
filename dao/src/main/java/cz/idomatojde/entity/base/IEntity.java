package cz.idomatojde.entity.base;

/**
 * Base marker interface identifying an entity
 *
 * @author Michal Hazdra
 */
public interface IEntity {

    /**
     * The unique identifier of the entity
     *
     * @return the ID assigned by the database
     */
    Long getId();
}
