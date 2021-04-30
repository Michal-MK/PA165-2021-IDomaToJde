package cz.idomatojde.dto.timetable;

import cz.idomatojde.dto.user.UserDTO;

import java.util.Objects;

/**
 * DTO representing timetable chat message for creation
 *
 * @author Jiri Vrbka
 */
public class AddTimetableChatMessageDTO {

    private UserDTO sender;

    private TimetableEntryDTO timetableEntry;

    private String text;

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    public TimetableEntryDTO getTimetableEntry() {
        return timetableEntry;
    }

    public void setTimetableEntry(TimetableEntryDTO timetableEntry) {
        this.timetableEntry = timetableEntry;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddTimetableChatMessageDTO)) return false;
        AddTimetableChatMessageDTO that = (AddTimetableChatMessageDTO) o;
        return Objects.equals(getSender(), that.getSender()) &&
                Objects.equals(getTimetableEntry(), that.getTimetableEntry()) &&
                Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimetableEntry(), getSender(), getText());
    }
}
