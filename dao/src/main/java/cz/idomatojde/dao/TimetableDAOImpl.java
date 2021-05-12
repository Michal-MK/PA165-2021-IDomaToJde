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


/**
 * DAO implementation for all Timetable related entities
 *
 * @author Michal Hazdra
 */
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
        entry.setEntryStart(newStart);
        entry.setLength(newDuration);
        updateEntry(entry);
    }

    @Override
    public void moveEntry(TimetableEntry entry, LocalTime newStart) {
        moveEntry(entry, newStart, entry.getLength());
    }

    @Override
    public void removeEntry(TimetableEntry entry) {
        em.remove(entry);
        em.flush();
        em.clear();
    }

    @Override
    public void updateEntry(TimetableEntry entry) {
        em.merge(entry);
    }

    @Override
    public Timetable getTimetable(User user, int year, int week) {
        return em.createQuery("select t from Timetable t where t.user = :user and t.year = :y and t.week = :w",
                Timetable.class)
                .setParameter("user", user)
                .setParameter("y", year)
                .setParameter("w", week)
                .getSingleResult();
    }

    @Override
    public TimetableEntry findEntry(long entryId) {
        return em.createQuery("select a from TimetableEntry a where a.id = :id", TimetableEntry.class)
                .setParameter("id", entryId)
                .getSingleResult();
    }

    public Timetable getTimetableForCurrentWeek(User user) {
        int week = LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int year = LocalDate.now().getYear();
        return getTimetable(user, year, week);
    }

    @Override
    public List<TimetableEntry> getAllTimetableEntries(long timetableId) {
        return em.createQuery("select t from Timetable t left join fetch t.entries where t.id = :id",
                Timetable.class)
                .setParameter("id", timetableId)
                .getSingleResult()
                .getEntries();
    }

    @Override
    public Timetable getByIdWithUser(long timetableId) {
        return em.createQuery("select t from Timetable t left join fetch t.user where t.id = :id",
                Timetable.class)
                .setParameter("id", timetableId)
                .getSingleResult();
    }
}
