package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.facade.CategoryFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import cz.idomatojde.rest.controllers.base.AuthState;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsible for all things concerning Categories
 *
 * @author Michal Hazdra
 */
@Api(tags = "Categories Endpoint")
@RestController
@RequestMapping("categories")
public class CategoryController extends
        AuthBaseRESTController<CategoryFacade, CategoryDTO, CategoryDTO> {
    @Inject
    public CategoryController(UserFacade userFacade, CategoryFacade categories) {
        super(userFacade, categories, true, false, true, true);
    }

    @GetMapping("byName/{name}")
    public ResponseEntity<CategoryDTO> getByName(@PathVariable String name) {
        return ok(facade.getByName(name));
    }

    @Override
    protected boolean isOwner(Long principalId, Long resourceId) {
        return false; // Nobody owns Categories
    }

    @Override
    protected boolean allowedToRegister(AuthState state, CategoryDTO categoryDTO) {
        return state.admin();
    }
}
