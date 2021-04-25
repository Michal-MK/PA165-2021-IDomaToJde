package cz.idomatojde.dto.timetable;

import java.util.List;

/**
 * DTO Holding the necessary information to populate the rendered timetable entry
 *
 * @author Michal Hazdra
 */
public class TimetableEntryDTO {

    private long timetableId;

    private int day;

    private List<TimetableChatMessageDTO> messages;

    public long getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(long timetableId) {
        this.timetableId = timetableId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<TimetableChatMessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<TimetableChatMessageDTO> messages) {
        this.messages = messages;
    }
}
