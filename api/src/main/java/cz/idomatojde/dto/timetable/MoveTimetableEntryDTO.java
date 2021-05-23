package cz.idomatojde.dto.timetable;

import cz.idomatojde.dto.base.DurationDTO;
import cz.idomatojde.dto.base.LocalTimeDTO;

public class MoveTimetableEntryDTO {
    private long timetableEntryId;
    private int day;
    private LocalTimeDTO time;
    private DurationDTO duration;

    public long getTimetableEntryId() {
        return timetableEntryId;
    }

    public void setTimetableEntryId(long timetableEntryId) {
        this.timetableEntryId = timetableEntryId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LocalTimeDTO getTime() {
        return time;
    }

    public void setTime(LocalTimeDTO time) {
        this.time = time;
    }

    public DurationDTO getDuration() {
        return duration;
    }

    public void setDuration(DurationDTO duration) {
        this.duration = duration;
    }
}
