package cz.idomatojde.dto.timetable;

/**
 * DTO representing change of timetable chat message`s text
 *
 * @author Jiri Vrbka
 */
public class ChangeTextTimetableChatMessageDTO {
    private Long id;

    private String text;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
