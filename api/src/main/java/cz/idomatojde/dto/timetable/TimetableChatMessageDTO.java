package cz.idomatojde.dto.timetable;

/**
 * DTO Holding the necessary information to populate the rendered timetable chat message
 *
 * @author Michal Hazdra
 */
public class TimetableChatMessageDTO {
    private Long id;

    private long userId;

    private long timetableEntryId;

    private String text;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTimetableEntryId() {
        return timetableEntryId;
    }

    public void setTimetableEntryId(long timetableEntryId) {
        this.timetableEntryId = timetableEntryId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
