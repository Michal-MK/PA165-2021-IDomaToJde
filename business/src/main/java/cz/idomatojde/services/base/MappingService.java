package cz.idomatojde.services.base;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Service to map DTOs to Entities and back
 *
 * @author Michal Hazdra
 */
public interface MappingService {

    /**
     * Maps a collection of objects to the requested class type
     *
     * @param objects    the collection to re-map
     * @param mapToClass the type to convert to
     * @param <T>        the type of the class
     * @return list of the re-mapped objects
     */
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Maps a single object to the requested class type
     *
     * @param obj        the object to re-map
     * @param mapToClass the type to convert to
     * @param <T>        the type of the class
     * @return the re-mapped object
     */
    <T> T mapTo(Object obj, Class<T> mapToClass);

    /**
     * Function to obtain the backing mapper interface
     *
     * @return the raw mapper interface
     */
    Mapper getMapper();
}
