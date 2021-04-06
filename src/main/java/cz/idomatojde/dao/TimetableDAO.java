package cz.idomatojde.dao;

import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;

import java.util.List;


public interface TimetableDAO {
    Timetable createTimetable(User user, int year, int week);

    Timetable getTimetable(Long timetableId);

    Timetable getTimetable(int year, int week);

    List<TimetableEntry> getAllTimetableEntries(Long timetableId);
}
