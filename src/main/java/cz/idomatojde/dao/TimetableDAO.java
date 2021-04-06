package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface TimetableDAO extends BaseDAO<Timetable> {
    Timetable createTimetable(User user, int year, int week);

    TimetableEntry createEntry(Timetable timetable, Offer offer, LocalTime start, Duration duration);

    void moveEntry(TimetableEntry entry, LocalDate newStart, Duration newDuration);

    void moveEntry(TimetableEntry entry, LocalDate newStart);

    void removeEntry(TimetableEntry entry);

    void updateEntry(TimetableEntry entry);

    Timetable getTimetable(User user, int year, int week);

    TimetableEntry findEntry(Long entryId);

    Timetable getTimetableForCurrentWeek(User user);

    List<TimetableEntry> getAllTimetableEntries(Long timetableId);
}
