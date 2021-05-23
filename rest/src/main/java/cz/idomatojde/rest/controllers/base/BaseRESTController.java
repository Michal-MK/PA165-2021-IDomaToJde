package cz.idomatojde.rest.controllers.base;

import cz.idomatojde.facade.base.BaseFacade;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Base class for all REST controller providing CRUD operations
 *
 * @param <TFacade> the type of the core facade
 * @param <TRegDto> the DTO used as a "Registration" DTO
 * @param <TDto>    the generic DTO of the core facade
 * @author Michal Hazdra
 */
public abstract class BaseRESTController<TFacade extends BaseFacade<TRegDto, TDto>, TRegDto, TDto> {

    protected final TFacade facade;

    public BaseRESTController(TFacade facade) {
        this.facade = facade;
    }

    @GetMapping("find/{id}")
    protected TDto getById(@PathVariable Long id) {
        return facade.getById(id);
    }

    @PutMapping(value = "register")
    protected long register(@RequestBody TRegDto regDto) {
        return facade.register(regDto);
    }

    @DeleteMapping(value = "delete")
    protected void delete(@RequestBody TDto dto) {
        facade.delete(dto);
    }

    @DeleteMapping(value = "deleteId/{id}")
    protected void delete(@PathVariable Long id) {
        facade.delete(id);
    }
}
