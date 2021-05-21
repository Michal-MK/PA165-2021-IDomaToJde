package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.facade.CategoryFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

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
        super(userFacade, categories, true);
    }

    @GetMapping("byName/{name}")
    public CategoryDTO getByName(@PathVariable String name) {
        return facade.getByName(name);
    }
}
