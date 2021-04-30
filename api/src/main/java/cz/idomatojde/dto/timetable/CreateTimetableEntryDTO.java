package cz.idomatojde.dto.timetable;

import cz.idomatojde.dto.offer.OfferDTO;

import java.time.Duration;
import java.time.LocalTime;

/**
 * @author Jiri Vrbka
 */
public class CreateTimetableEntryDTO {
    private TimetableDTO timetable;
    private OfferDTO offer;
    private LocalTime entryStart;
    private Duration length;


    public TimetableDTO getTimetable() {
        return timetable;
    }

    public void setTimetable(TimetableDTO timetable) {
        this.timetable = timetable;
    }

    public OfferDTO getOffer() {
        return offer;
    }

    public void setOffer(OfferDTO offer) {
        this.offer = offer;
    }

    public LocalTime getEntryStart() {
        return entryStart;
    }

    public void setEntryStart(LocalTime entryStart) {
        this.entryStart = entryStart;
    }

    public Duration getLength() {
        return length;
    }

    public void setLength(Duration length) {
        this.length = length;
    }

}
