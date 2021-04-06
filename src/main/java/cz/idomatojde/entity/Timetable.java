package cz.idomatojde.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private int week;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @Column(name = "YEAR_VALUE")
    private int year;

    @OneToMany(mappedBy = "timetable", targetEntity = TimetableEntry.class)
    private List<TimetableEntry> entries;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int yearVal) {
        this.year = yearVal;
    }

    public List<TimetableEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TimetableEntry> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timetable)) return false;
        Timetable timetable = (Timetable) o;
        return getWeek() == timetable.getWeek() &&
                getYear() == timetable.getYear() &&
                Objects.equals(getUser(), timetable.getUser()) &&
                Objects.equals(getEntries(), timetable.getEntries());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeek(), getYear(), getEntries());
    }
}
