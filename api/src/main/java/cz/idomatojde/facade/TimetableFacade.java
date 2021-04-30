package cz.idomatojde.facade;

import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;

/**
 * Facade responsible for all things concerning timetables
 *
 * @author Michal Hazdra
 */
public interface TimetableFacade {

    /**
     * Create a new timetable
     *
     * @param timetableDTO necessary timetable creation information
     * @return unique id of the timetable
     */
    long addTimetable(AddTimetableDTO timetableDTO);

    /**
     * Retrieve all information related to a specific timetable
     *
     * @param timetableId the unique timetable identifier
     * @return timetable with all entries
     */
    TimetableDTO getTimetable(long timetableId);

    /**
     * Creates entry
     *
     * @param entryDto dto for creation
     * @return timetable with all entries
     */
    long createEntry(CreateTimetableEntryDTO entryDto);

    /**
     * Get entry by id
     * @param entryId unique identifier
     * @return DTO representing entry
     */
    TimetableEntryDTO getEntryById(long entryId);
}
