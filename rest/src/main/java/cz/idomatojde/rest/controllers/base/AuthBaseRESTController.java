package cz.idomatojde.rest.controllers.base;

import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.facade.base.BaseFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Base class for all REST controllers needing authentication
 *
 * @param <TFacade> the type of the core facade
 * @param <TRegDto> the DTO used as a "Registration" DTO
 * @param <TDto>    the generic DTO of the core facade
 * @author Michal Hazdra
 */
public abstract class AuthBaseRESTController<TFacade extends BaseFacade<TRegDto, TDto>, TRegDto, TDto> {

    protected final UserFacade userFacade;

    protected final TFacade facade;

    private final boolean allowGet;

    public AuthBaseRESTController(UserFacade users, TFacade coreFacade) {
        facade = coreFacade;
        userFacade = users;
        allowGet = false;
    }

    public AuthBaseRESTController(UserFacade users, TFacade coreFacade, boolean allowGet) {
        facade = coreFacade;
        userFacade = users;
        this.allowGet = allowGet;
    }

    @GetMapping("find/{id}")
    protected TDto getById(@RequestHeader(value = "token") String token, @PathVariable Long id) {
        if (!allowGet && isAuthenticated(token) == null) {
            return null;
        }
        return facade.getById(id);
    }

    @PutMapping(value = "register")
    protected long register(@RequestHeader(value = "token") String token, TRegDto regDto) {
        if (isAuthenticated(token) == null) {
            return -1;
        }
        return facade.register(regDto);
    }

    @DeleteMapping(value = "delete")
    protected void delete(@RequestHeader(value = "token") String token, TDto dto) {
        if (isAuthenticated(token) == null) {
            return;
        }
        facade.delete(dto);
    }

    @DeleteMapping(value = "deleteId/{id}")
    protected void delete(@RequestHeader(value = "token") String token, @PathVariable Long id) {
        if (isAuthenticated(token) == null) {
            return;
        }
        facade.delete(id);
    }

    protected UserDTO isAuthenticated(String token) {
        if (token.isBlank()) {
            return null;
        }
        return userFacade.authenticate(token);
    }

    protected ResponseEntity<Void> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    protected <T> ResponseEntity<T> forbidden(T object) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(object);
    }
}
