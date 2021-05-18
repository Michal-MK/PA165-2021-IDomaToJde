package cz.idomatojde.services.facades;

import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.facade.TimetableFacade;
import cz.idomatojde.services.OfferService;
import cz.idomatojde.services.TimetableService;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.base.MappingService;
import cz.idomatojde.services.facades.base.BaseFacadeImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Michal Hazdra
 */
@Service
@Transactional
public class TimetableFacadeImpl extends BaseFacadeImpl<AddTimetableDTO, TimetableDTO, Timetable> implements TimetableFacade {

    private final TimetableService timetableService;

    private final UserService userService;

    private final OfferService offerService;

    @Inject
    public TimetableFacadeImpl(TimetableService timetableService, UserService userService,
                               OfferService offerService, MappingService mapService) {
        super(timetableService, mapService, TimetableDTO.class, Timetable.class);
        this.timetableService = timetableService;
        this.userService = userService;
        this.offerService = offerService;
    }

    @Override
    public long registerEntry(CreateTimetableEntryDTO entryDto) {
        var timetable = timetableService.getById(entryDto.getTimetable().getId());
        var offer = offerService.getById(entryDto.getOffer().getId());

        var entry = timetableService.createEntry(timetable, offer,
                mapService.fromLocalTimeDTO(entryDto.getEntryStart()), mapService.fromDurationDTO(entryDto.getLength()));

        return entry.getId();
    }

    @Override
    public TimetableEntryDTO getEntryById(long entryId) {
        var entry = timetableService.findEntry(entryId);
        return mapService.toTimetableEntryDTO(entry);
    }
}
