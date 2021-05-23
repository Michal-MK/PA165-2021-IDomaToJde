package cz.idomatojde.rest.controllers.base;

import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.facade.base.BaseFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final boolean ownerOnlyDelete;
    private final boolean adminOnlyDelete;
    private final boolean adminOnlyRegister;

    public AuthBaseRESTController(UserFacade users, TFacade coreFacade, boolean allowUnauthenticatedGet,
                                  boolean ownerOnlyDelete, boolean adminOnlyDelete, boolean adminOnlyRegister) {
        facade = coreFacade;
        userFacade = users;
        this.allowUnauthenticatedGet = allowUnauthenticatedGet;
        this.ownerOnlyDelete = ownerOnlyDelete;
        this.adminOnlyDelete = adminOnlyDelete;
        this.adminOnlyRegister = adminOnlyRegister;
    }

    @GetMapping("find/{id}")
    protected ResponseEntity<TDto> getById(@RequestHeader(value = "token", required = false) String token, @PathVariable Long id) {
        AuthState auth = isAuthenticated(token);
        if (!allowUnauthenticatedGet && !auth.authenticated()) return unauthorized(null);

        return ok(facade.getById(id));
    }

    @PutMapping(value = "register")
    protected ResponseEntity<Long> register(@RequestHeader(value = "token") String token, @RequestBody TRegDto regDto) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);
        if (adminOnlyRegister && !auth.admin()) return forbidden(null);
        if (!allowedToRegister(auth, regDto)) return forbidden(null);

        return ok(facade.register(regDto));
    }

    @DeleteMapping(value = "delete")
    @Deprecated(since = "This method should not exist..., currently does nothing!")
    protected ResponseEntity<Void> delete(@RequestHeader(value = "token") String token, @RequestBody TDto dto) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();

        // facade.delete(dto);
        return ok().build();
    }

    @DeleteMapping(value = "deleteId/{id}")
    protected ResponseEntity<Void> delete(@RequestHeader(value = "token") String token, @PathVariable Long id) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();
        if (adminOnlyDelete && !auth.admin()) return forbidden();
        if (ownerOnlyDelete && isOwner(auth.principalId(), id)) return unauthorized();

        facade.delete(id);
        return ok().build();
    }

    protected AuthState isAuthenticated(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        return new AuthState(userFacade.authenticate(token));
    }

    protected abstract boolean isOwner(Long principalId, Long resourceId);
    protected abstract boolean allowedToRegister(AuthState state, TRegDto dto);

    protected ResponseEntity<Void> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    protected <T> ResponseEntity<T> unauthorized(T object) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(object);
    }

    protected ResponseEntity<Void> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    protected <T> ResponseEntity<T> forbidden(T object) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(object);
    }
}
