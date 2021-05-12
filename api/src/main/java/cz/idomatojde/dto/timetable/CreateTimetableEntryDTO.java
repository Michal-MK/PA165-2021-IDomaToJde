package cz.idomatojde.dto.timetable;

import cz.idomatojde.dto.base.DurationDTO;
import cz.idomatojde.dto.base.LocalTimeDTO;
import cz.idomatojde.dto.offer.OfferDTO;

/**
 * DTO Responsible for new timetable entry creation
 *
 * @author Jiri Vrbka
 */
public class CreateTimetableEntryDTO {
    private TimetableDTO timetable;
    private OfferDTO offer;
    private LocalTimeDTO entryStart;
    private DurationDTO length;

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
}
