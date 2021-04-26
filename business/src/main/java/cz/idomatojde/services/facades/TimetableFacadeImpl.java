package cz.idomatojde.services.facades;

import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.User;
import cz.idomatojde.facade.TimetableFacade;
import cz.idomatojde.services.TimetableService;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.base.MappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class TimetableFacadeImpl implements TimetableFacade {

    private final TimetableService timetableService;

    private final UserService userService;

    private final MappingService mapService;

    @Inject
    public TimetableFacadeImpl(TimetableService timetableService, UserService userService, MappingService mapService) {
        this.timetableService = timetableService;
        this.userService = userService;
        this.mapService = mapService;
    }

    @Override
    public long addTimetable(AddTimetableDTO timetableDTO) {
        User u = userService.getById(timetableDTO.getUserId());

        Timetable t = timetableService.createTimetable(u, timetableDTO.getYear(), timetableDTO.getWeek());
        return t.getId();
    }

    @Override
    public TimetableDTO getTimetable(long timetableId) {
        Timetable t = timetableService.getTimetableWithEntries(timetableId);

        return mapService.mapTo(t, TimetableDTO.class);
    }
}
