package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
import cz.idomatojde.dto.timetable.MoveTimetableEntryDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.facade.TimetableFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import cz.idomatojde.rest.controllers.base.AuthState;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsible for all things concerning Timetables
 *
 * @author Michal Hazdra
 */
@Api(tags = "Timetables Endpoint")
@RestController
@RequestMapping("api/timetables")
public class TimetableController extends
        AuthBaseRESTController<TimetableFacade, AddTimetableDTO, TimetableDTO> {
    @Inject
    public TimetableController(UserFacade userFacade, TimetableFacade timetables) {
        super(userFacade, timetables, false, false, true, false);
    }

    @GetMapping("entry/{entryId}")
    ResponseEntity<TimetableEntryDTO> getEntryById(@RequestHeader(value = "token") String token, @PathVariable long entryId) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);
        if (!relatedOnlyPermission(auth, entryId)) return forbidden(null);

        return ok(facade.getEntryById(entryId));
    }

//  @GetMapping("entry/ofOffer/{offerId}") TODO

    @GetMapping("forWeek/current")
    ResponseEntity<TimetableDTO> getForCurrentWeek(@RequestHeader(value = "token") String token) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);

        return ok(facade.getTimetableForCurrentWeek(auth.principalId()));
    }

    @GetMapping("forWeek/{year}/{week}")
    ResponseEntity<TimetableDTO> getForWeek(@RequestHeader(value = "token") String token, @PathVariable int year, @PathVariable int week) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);

        return ok(facade.getTimetableForDate(auth.principalId(), year, week));
    }

    @PutMapping("registerEntry")
    ResponseEntity<Void> registerEntry(@RequestHeader(value = "token") String token, @RequestBody CreateTimetableEntryDTO dto) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();

        facade.registerEntry(dto);
        return ok().build();
    }

    @PostMapping("moveEntry")
    ResponseEntity<Void> moveEntry(@RequestHeader(value = "token") String token, @RequestBody MoveTimetableEntryDTO dto) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();
        if (!offerAuthorOnlyPermission(auth, dto.getTimetableEntryId())) return forbidden();

        facade.moveTimetableEntry(dto);
        return ok().build();
    }

    private boolean relatedOnlyPermission(AuthState principal, long entryId) {
        boolean timetableOwner = facade.getFromEntry(entryId).getUserInfo().getId() == principal.principalId();
        return timetableOwner || offerAuthorOnlyPermission(principal, entryId) || principal.admin();
    }

    private boolean offerAuthorOnlyPermission(AuthState principal, long timetableEntryId) {
        TimetableEntryDTO entry = facade.getEntryById(timetableEntryId);
        return entry.getOffer().getOwner().getId() == principal.principalId() || principal.admin();
    }

    @Override
    protected boolean isOwner(Long principalId, Long resourceId) {
        return facade.getById(resourceId).getUserInfo().getId().equals(principalId);
    }

    @Override
    protected boolean allowedToRegister(AuthState state, AddTimetableDTO addTimetableDTO) {
        return state.authenticated();
    }
}
