package cz.idomatojde.dto.timetable;

/**
 * DTO Responsible for new timetable creation
 *
 * @author Michal Hazdra
 */
public class AddTimetableDTO {

    private Long userId;

    private int year;

    private int week;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
