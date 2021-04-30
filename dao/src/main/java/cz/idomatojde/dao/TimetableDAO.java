package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;


/**
 * API for {@link Timetable} and {@link TimetableEntry} Entities
 *
 * @author Michal Hazdra
 */
public interface TimetableDAO extends BaseDAO<Timetable> {


    /**
     * Creates a {@link Timetable} from the required components
     *
     * @param user the {@link User} the timetable belongs to
     * @param year the year of the timetable
     * @param week the week of the year
     * @return the newly created {@link Timetable}
     */
    Timetable createTimetable(User user, int year, int week);


    /**
     * Creates a new {@link TimetableEntry} from the required components
     *
     * @param timetable the {@link Timetable} this entry will be part of
     * @param offer     the {@link Offer} that initiated the entry
     * @param start     the time of day when the entry starts
     * @param duration  the duration of the entry
     * @return the newly created {@link TimetableEntry}
     */
    TimetableEntry createEntry(Timetable timetable, Offer offer, LocalTime start, Duration duration);


    /**
     * Function to move the {@link TimetableEntry} within the day
     *
     * @param entry       the {@link TimetableEntry} to move
     * @param newStart    the new starting time of the entry
     * @param newDuration new duration of the entry
     */
    void moveEntry(TimetableEntry entry, LocalTime newStart, Duration newDuration);


    /**
     * Function to move the {@link TimetableEntry} within the day, keeping the duration
     *
     * @param entry    the {@link TimetableEntry} to move
     * @param newStart the new starting time of the entry
     */
    void moveEntry(TimetableEntry entry, LocalTime newStart);


    /**
     * Removes the {@link TimetableEntry} from the database
     *
     * @param entry the entry to remove
     */
    void removeEntry(TimetableEntry entry);


    /**
     * Updates the {@link TimetableEntry} in the database
     *
     * @param entry the entry to update
     */
    void updateEntry(TimetableEntry entry);


    /**
     * Function to obtain the {@link Timetable} for a given {@link User} based on the date
     *
     * @param user the {@link User} the {@link Timetable} belongs to
     * @param year the year to search in
     * @param week the week to search in
     * @return the found {@link Timetable} object
     */
    Timetable getTimetable(User user, int year, int week);


    /**
     * Function to obtain the {@link TimetableEntry}
     *
     * @param entryId the id to search for
     * @return the {@link TimetableEntry} object
     */
    TimetableEntry findEntry(long entryId);


    /**
     * Function to obtain the {@link Timetable} for the current week
     *
     * @param user the {@link User} the {@link Timetable} belongs to
     * @return the {@link Timetable} object
     */
    Timetable getTimetableForCurrentWeek(User user);


    /**
     * Function to obtain all {@link TimetableEntry} for a given {@link Timetable} ID
     *
     * @param timetableId the id of the {@link Timetable}
     * @return the list of all {@link TimetableEntry}
     */
    List<TimetableEntry> getAllTimetableEntries(long timetableId);

    /**
     * Same as getById but with the {@link User} field populated
     * @param timetableId the id of the {@link Timetable}
     * @return the {@link Timetable} object
     */
    Timetable getByIdWithUser(long timetableId);
}
