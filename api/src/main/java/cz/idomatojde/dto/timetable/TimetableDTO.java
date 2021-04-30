package cz.idomatojde.dto.timetable;

import cz.idomatojde.dto.user.UserContactInfoDTO;

import java.util.List;

/**
 * DTO Holding the necessary information to populate the rendered timetable
 *
 * @author Michal Hazdra
 */
public class TimetableDTO {

    private UserContactInfoDTO userInfo;

    private int year;
    private int week;

    private List<TimetableEntryDTO> entries;

    public UserContactInfoDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserContactInfoDTO userInfo) {
        this.userInfo = userInfo;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public List<TimetableEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<TimetableEntryDTO> entries) {
        this.entries = entries;
    }
}
