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
 * API for TimetableChatMessage DTOs
 * @author Michal Hazdra
 */
public interface TimetableDAO extends BaseDAO<Timetable> {


    /** Creates a Timetable from the required components
     * @param user the user the timetable belongs to
     * @param year the year of the timetable
     * @param week the week of the year
     * @return the newly created timetable
     */
    Timetable createTimetable(User user, int year, int week);


    /** Creates a new TimetableEntry from the required components
     * @param timetable the Timetable this entry will be part of
     * @param offer the offer that initiated the entry
     * @param start the time of day when the entry starts
     * @param duration the duration of the entry
     * @return the newly created TimetableEntry
     */
    TimetableEntry createEntry(Timetable timetable, Offer offer, LocalTime start, Duration duration);


    /** Function to move an entry within the day
     * @param entry the entry to move
     * @param newStart the new starting time of the entry
     * @param newDuration new duration of the entry
     */
    void moveEntry(TimetableEntry entry, LocalTime newStart, Duration newDuration);


    /** Function to move an entry within the day, keeping the duration
     * @param entry the entry to move
     * @param newStart the new starting time of the entry
     */
    void moveEntry(TimetableEntry entry, LocalTime newStart);


    /** Removes the entry from the database
     * @param entry the entry to remove
     */
    void removeEntry(TimetableEntry entry);


    /** Updates the entry in the database
     * @param entry the entry to update
     */
    void updateEntry(TimetableEntry entry);


    /** Function to obtain the timetable for a given user based on the date
     * @param user the user the timetable belongs to
     * @param year the year to search in
     * @param week the week to search in
     * @return the found Timetable object
     */
    Timetable getTimetable(User user, int year, int week);


    /** Function to obtain the TimetableEntry
     * @param entryId the id to search for
     * @return the TimetableEntry object
     */
    TimetableEntry findEntry(Long entryId);


    /** Function to obtain the Timetable for the current week
     * @param user the user the timetable belongs to
     * @return the Timetable object
     */
    Timetable getTimetableForCurrentWeek(User user);


    /** Function to obtain all TimetableEntries of a given Timetable id
     * @param timetableId the id of the timetable
     * @return the list of all TimetableEntries
     */
    List<TimetableEntry> getAllTimetableEntries(Long timetableId);
}
