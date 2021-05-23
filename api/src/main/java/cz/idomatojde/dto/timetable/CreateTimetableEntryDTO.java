package cz.idomatojde.dto.timetable;

import cz.idomatojde.dto.base.DurationDTO;
import cz.idomatojde.dto.base.LocalTimeDTO;

/**
 * DTO Responsible for new timetable entry creation
 *
 * @author Michal Hazdra
 */
public class CreateTimetableEntryDTO {

    private Long timetableId;
    private Long offerId;
    private LocalTimeDTO entryStart;
    private DurationDTO length;
    private String description;
    private int day;

    public Long getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(Long timetableId) {
        this.timetableId = timetableId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public LocalTimeDTO getEntryStart() {
        return entryStart;
    }

    public void setEntryStart(LocalTimeDTO entryStart) {
        this.entryStart = entryStart;
    }

    public DurationDTO getLength() {
        return length;
    }

    public void setLength(DurationDTO length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
