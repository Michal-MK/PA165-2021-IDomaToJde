package cz.idomatojde.services;

import cz.idomatojde.dao.TimetableDAO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import cz.idomatojde.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Michal Hazdra
 */
@Service
public class TimetableServiceImpl extends BaseServiceImpl<Timetable> implements TimetableService {

    private final TimetableDAO timetables;

    @Inject
    public TimetableServiceImpl(TimetableDAO timetables) {
        super(timetables);
        this.timetables = timetables;
    }

    @Override
    public Timetable createTimetable(User user, int year, int week) {
        return timetables.createTimetable(user, year, week);
    }


    @Override
    public TimetableEntry createEntry(Timetable timetable, Offer offer, LocalTime start, Duration duration) {
        return timetables.createEntry(timetable, offer, start, duration);
    }


    @Override
    public void moveEntry(TimetableEntry entry, LocalTime newStart, Duration newDuration) {
        timetables.moveEntry(entry, newStart, newDuration);
    }


    @Override
    public void moveEntry(TimetableEntry entry, LocalTime newStart) {
        timetables.moveEntry(entry, newStart);
    }


    @Override
    public void removeEntry(TimetableEntry entry) {
        timetables.removeEntry(entry);
    }


    @Override
    public void updateEntry(TimetableEntry entry) {
        timetables.updateEntry(entry);
    }


    @Override
    public Timetable getTimetable(User user, int year, int week) {
        return timetables.getTimetable(user, year, week);
    }


    @Override
    public TimetableEntry findEntry(Long entryId) {
        return timetables.findEntry(entryId);
    }


    @Override
    public Timetable getTimetableForCurrentWeek(User user) {
        return timetables.getTimetableForCurrentWeek(user);
    }


    @Override
    public List<TimetableEntry> getAllTimetableEntries(Long timetableId) {
        return timetables.getAllTimetableEntries(timetableId);
    }
}
