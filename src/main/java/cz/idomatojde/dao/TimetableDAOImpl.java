package cz.idomatojde.dao;

import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.List;

@Repository
public class TimetableDAOImpl implements TimetableDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Timetable createTimetable(User user, int year, int week) {
        Timetable tt = new Timetable();
        tt.setYear(year);
        tt.setWeek(week);
        tt.setUser(user);

        em.persist(tt);
        return tt;
    }

    @Override
    @Transactional
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
    @Transactional
    public void removeEntry(TimetableEntry entry) {
        em.remove(entry);
    }

    @Override
    public Timetable getTimetable(Long timetableId) {
        return em.createQuery("select t from Timetable t where t.id = :id", Timetable.class)
                .setParameter("id", timetableId)
                .getSingleResult();
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
}
