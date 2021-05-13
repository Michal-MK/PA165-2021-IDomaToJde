package cz.idomatojde.facade;

import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
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
     * @param entryId unique identifier
     * @return DTO representing entry
     */
    TimetableEntryDTO getEntryById(long entryId);
}
