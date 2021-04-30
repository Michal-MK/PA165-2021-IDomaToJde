package cz.idomatojde.services.facades;

import cz.idomatojde.configuration.CustomMapper;
import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.User;
import cz.idomatojde.facade.TimetableFacade;
import cz.idomatojde.services.OfferService;
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
    private OfferService offerService;

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

        TimetableDTO ret = mapService.mapTo(t, TimetableDTO.class);
        ret.setUserInfo(mapService.mapTo(t.getUser(), UserContactInfoDTO.class));
        ret.setId(timetableId);
        return ret;
    }

    @Override
    public long createEntry(CreateTimetableEntryDTO entryDto) {
        var timetable = timetableService.getById(entryDto.getTimetable().getId());//mapService.mapTo(entryDto.getTimetable(), Timetable.class);
        var offer =  offerService.getById(entryDto.getOffer().getId()); //mapService.mapTo(entryDto.getOffer(), Offer.class);

        var entry =
                timetableService.createEntry(timetable, offer, entryDto.getEntryStart(), entryDto.getLength());

        return entry.getId();
    }

    @Override
    public TimetableEntryDTO getEntryById(long entryId) {
        var entry = timetableService.findEntry(entryId);
        return CustomMapper.toTimetableEntryDTO(entry);//TODO mapService.mapTo(entry, TimetableEntryDTO.class);
    }
}
