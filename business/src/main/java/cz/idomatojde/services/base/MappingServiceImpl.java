package cz.idomatojde.services.base;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Michal Hazdra
 */
@Service
public class MappingServiceImpl implements MappingService {

    private final Mapper dozer;

    @Inject
    public MappingServiceImpl(Mapper dozer) {
        this.dozer = dozer;
    }

    /**
     * {@inheritDoc}
     */
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    /**
     * {@inheritDoc}
     */
    public <T> T mapTo(Object obj, Class<T> mapToClass) {
        return dozer.map(obj, mapToClass);
    }

    /**
     * {@inheritDoc}
     */
    public Mapper getMapper() {
        return dozer;
    }
}
