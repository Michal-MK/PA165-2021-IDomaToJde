package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.facade.TimetableFacade;
import cz.idomatojde.rest.controllers.base.BaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Controller responsible for all things concerning Timetables
 *
 * @author Michal Hazdra
 */
@Api(tags = "Timetables Endpoint")
@RestController
@RequestMapping("timetables")
public class TimetableController extends BaseRESTController<TimetableFacade, AddTimetableDTO, TimetableDTO> {
    @Inject
    public TimetableController(TimetableFacade timetables) {
        super(timetables);
    }

    @GetMapping("entry/{entryId}")
    TimetableEntryDTO getEntryById(@PathVariable long entryId){
        return facade.getEntryById(entryId);
    }

    @PutMapping("registerEntry")
    void registerEntry(CreateTimetableEntryDTO dto) {
        facade.registerEntry(dto);
    }
}
