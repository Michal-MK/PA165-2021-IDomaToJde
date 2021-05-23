package cz.idomatojde.dto.timetable;

import java.util.Objects;

/**
 * DTO Responsible for new timetable chat message creation
 *
 * @author Michal Hazdra
 */
public class AddTimetableChatMessageDTO {

    private Long senderId;

    private Long timetableEntryId;

    private String text;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getTimetableEntryId() {
        return timetableEntryId;
    }

    public void setTimetableEntryId(Long timetableEntryId) {
        this.timetableEntryId = timetableEntryId;
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
        return Objects.equals(getSenderId(), that.getSenderId()) && Objects.equals(getTimetableEntryId(), that.getTimetableEntryId()) && Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSenderId(), getTimetableEntryId(), getText());
    }
}
