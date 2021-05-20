package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.facade.CategoryFacade;
import cz.idomatojde.rest.controllers.base.BaseRESTController;
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
public class CategoryController extends BaseRESTController<CategoryFacade, CategoryDTO, CategoryDTO> {
    @Inject
    public CategoryController(CategoryFacade categories) {
        super(categories);
    }

    @GetMapping("byName/{name}")
    public CategoryDTO getByName(@PathVariable String name) {
        return facade.getByName(name);
    }
}
