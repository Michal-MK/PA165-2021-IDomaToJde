package cz.idomatojde.facade;

import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
import cz.idomatojde.dto.timetable.MoveTimetableEntryDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.facade.base.BaseFacade;

/**
 * Facade responsible for all things concerning timetables
 *
 * @author Michal Hazdra
 */
public interface TimetableFacade extends BaseFacade<AddTimetableDTO, TimetableDTO> {

    /**
     * Creates entry
     *
     * @param entryDto dto for creation
     * @return timetable with all entries
     */
    long registerEntry(CreateTimetableEntryDTO entryDto);

    /**
     * Get entry by id
     *
     * @param entryId unique identifier of the entry
     * @return DTO representing entry
     */
    TimetableEntryDTO getEntryById(long entryId);

    /**
     * Get entry by id
     *
     * @param timetableId unique identifier of the timetable
     * @return DTO representing the timetable
     */
    TimetableDTO getWithEntries(long timetableId);

    /**
     * Get timetable by an existing entry within the timetable
     *
     * @param entryId unique identifier of the entry
     * @return DTO representing the timetable
     */
    TimetableDTO getFromEntry(long entryId);

    /**
     * Get timetable for current week
     *
     * @param userId unique identifier of the user the timetable belongs to
     * @return DTO representing the timetable
     */
    TimetableDTO getTimetableForCurrentWeek(long userId);

    /**
     * Get timetable for current week
     *
     * @param userId unique identifier of the user the timetable belongs to
     * @param year the year... what do you expect?
     * @param week the week within the specified year
     * @return DTO representing the timetable
     */
    TimetableDTO getTimetableForDate(long userId, int year, int week);

    /**
     * Move timetable entry by days/hours and duration
     *
     * @param moveEntryDTO the information which entry, the day and time + duration
     */
    void moveTimetableEntry(MoveTimetableEntryDTO moveEntryDTO);
}
