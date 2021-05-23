package cz.idomatojde.dto.timetable;

import cz.idomatojde.dto.base.DurationDTO;
import cz.idomatojde.dto.base.LocalTimeDTO;
import cz.idomatojde.dto.offer.OfferDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO Holding the necessary information to populate the rendered timetable entry
 *
 * @author Jiri Vrbka
 */
public class TimetableEntryDTO {

    private Long id;

    private TimetableDTO timetable;

    private OfferDTO offer;

    private int day;

    private LocalTimeDTO entryStart;

    private DurationDTO length;

    private String description;

    private List<TimetableChatMessageDTO> messages = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

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

    public List<TimetableChatMessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<TimetableChatMessageDTO> messages) {
        this.messages = messages;
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
