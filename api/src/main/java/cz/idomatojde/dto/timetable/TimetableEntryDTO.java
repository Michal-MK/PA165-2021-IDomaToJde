package cz.idomatojde.dto.timetable;

import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableChatMessage;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    private Timetable timetable;

    private Offer offer;

    private int day;

    private LocalTime entryStart;

    private Duration length;

    private String description;

    private List<TimetableChatMessage> messages = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
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

    public List<TimetableChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<TimetableChatMessage> messages) {
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
