package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAOImpl;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.List;

@Repository
public class TimetableDAOImpl extends BaseDAOImpl<Timetable> implements TimetableDAO {

    public TimetableDAOImpl() {
        super(Timetable.class);
    }

    @Override
    public Timetable createTimetable(User user, int year, int week) {
        Timetable tt = new Timetable();
        tt.setYear(year);
        tt.setWeek(week);
        tt.setUser(user);

        em.persist(tt);
        return tt;
    }

    @Override
    public TimetableEntry createEntry(Timetable timetable, Offer offer, LocalTime start, Duration duration) {
        TimetableEntry entry = new TimetableEntry();
        entry.setEntryStart(start);
        entry.setTimetable(timetable);
        entry.setLength(duration);
        entry.setOffer(offer);

        em.persist(entry);

        timetable.getEntries().add(entry);
        return entry;
    }

    @Override
    public void moveEntry(TimetableEntry entry, LocalTime newStart, Duration newDuration) {
        em.createQuery("update TimetableEntry te set te.entryStart = :start, te.length = :len")
                .setParameter("start", newStart)
                .setParameter("len", newDuration)
                .executeUpdate();
    }

    @Override
    public void moveEntry(TimetableEntry entry, LocalTime newStart) {
        moveEntry(entry, newStart, entry.getLength());
    }

    @Override
    public void removeEntry(TimetableEntry entry) {
        em.remove(entry);
    }

    @Override
    public void updateEntry(TimetableEntry entry) {
        em.createQuery("update TimetableEntry te set te.entryStart = :start, te.length = :len, " +
                "te.description = :desc, te.day = :day, te.messages = :msg, te.offer = :offer")
                .setParameter("start", entry.getEntryStart())
                .setParameter("len", entry.getLength())
                .setParameter("desc", entry.getDescription())
                .setParameter("day", entry.getDay())
                .setParameter("msg", entry.getMessages())
                .setParameter("offer", entry.getOffer())
                .executeUpdate();
    }

    @Override
    public Timetable getTimetable(User user, int year, int week) {
        return em.createQuery("select t from Timetable t where" +
                " t.user = :user and t.year = :y and t.week = :w",
                Timetable.class)
                .setParameter("user", user)
                .setParameter("y", year)
                .setParameter("w", week)
                .getSingleResult();
    }

    @Override
    public TimetableEntry findEntry(Long entryId) {
        return null;
    }

    public Timetable getTimetableForCurrentWeek(User user) {
        int week = LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int year = LocalDate.now().getYear();
        return getTimetable(user, year, week);
    }

    @Override
    public List<TimetableEntry> getAllTimetableEntries(Long timetableId) {
        return em.createQuery("select t from Timetable t join fetch t.entries where t.id = :id", Timetable.class)
                .setParameter("id", timetableId)
                .getSingleResult()
                .getEntries();
    }

    @Override
    public void update(Timetable timetable) {
        em.createQuery("update Timetable t set t.year = :year, t.week = :week, t.entries = :entries")
                .setParameter("year", timetable.getYear())
                .setParameter("week", timetable.getWeek())
                .setParameter("entries", timetable.getEntries())
                .executeUpdate();
    }
}
