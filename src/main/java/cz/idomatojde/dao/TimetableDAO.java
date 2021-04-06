package cz.idomatojde.dao;

import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;


public interface TimetableDAO {
    Timetable createTimetable(User user, int year, int week);

    TimetableEntry createEntry(Timetable timetable, Offer offer, LocalTime start, Duration duration);

    void removeEntry(TimetableEntry entry);

    Timetable getTimetable(Long timetableId);

    Timetable getTimetable(User user, int year, int week);

    Timetable getTimetableForCurrentWeek(User user);

    List<TimetableEntry> getAllTimetableEntries(Long timetableId);
}
