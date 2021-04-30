package cz.idomatojde.dto.timetable;

import cz.idomatojde.dto.offer.OfferDTO;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO Holding the necessary information to populate the rendered timetable entry
 *
 * @author Jiri Vrbka
 */
public class TimetableEntryDTO {

    private long id;

    private TimetableDTO timetable;

    private OfferDTO offer;

    private int day;

    private LocalTime entryStart;

    private Duration length;

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
