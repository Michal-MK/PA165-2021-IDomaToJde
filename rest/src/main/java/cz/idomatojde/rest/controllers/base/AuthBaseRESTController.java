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

import static org.springframework.http.ResponseEntity.ok;

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

    private final boolean allowUnauthenticatedGet;

    public AuthBaseRESTController(UserFacade users, TFacade coreFacade) {
        facade = coreFacade;
        userFacade = users;
        allowUnauthenticatedGet = false;
    }

    public AuthBaseRESTController(UserFacade users, TFacade coreFacade, boolean allowUnauthenticatedGet) {
        facade = coreFacade;
        userFacade = users;
        this.allowUnauthenticatedGet = allowUnauthenticatedGet;
    }

    @GetMapping("find/{id}")
    protected ResponseEntity<TDto> getById(@RequestHeader(value = "token") String token, @PathVariable Long id) {
        if (!allowUnauthenticatedGet && notAuthenticated(token)) return forbidden(null);

        return ok(facade.getById(id));
    }

    @PutMapping(value = "register")
    protected ResponseEntity<Long> register(@RequestHeader(value = "token") String token, TRegDto regDto) {
        if (notAuthenticated(token)) return forbidden(-1L);

        return ok(facade.register(regDto));
    }

    @DeleteMapping(value = "delete")
    protected ResponseEntity<Void> delete(@RequestHeader(value = "token") String token, TDto dto) {
        if (notAuthenticated(token)) return forbidden();

        facade.delete(dto);
        return ok().build();
    }

    @DeleteMapping(value = "deleteId/{id}")
    protected ResponseEntity<Void> delete(@RequestHeader(value = "token") String token, @PathVariable Long id) {
        if (notAuthenticated(token)) return forbidden();

        facade.delete(id);
        return ok().build();
    }

    protected UserDTO isAuthenticated(String token) {
        if (token.isBlank()) {
            return null;
        }
        return userFacade.authenticate(token);
    }

    protected boolean notAuthenticated(String token) {
        return isAuthenticated(token) == null;
    }

    protected ResponseEntity<Void> forbidden() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    protected <T> ResponseEntity<T> forbidden(T object) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(object);
    }
}
