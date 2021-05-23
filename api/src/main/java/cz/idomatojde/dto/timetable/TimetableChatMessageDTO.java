package cz.idomatojde.dto.timetable;

/**
 * DTO Holding the necessary information to populate the rendered timetable chat message
 *
 * @author Michal Hazdra
 */
public class TimetableChatMessageDTO {

    private Long id;

    private Long userId;

    private Long timetableEntryId;

    private String text;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
