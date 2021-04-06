package cz.idomatojde.dao;

import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
    public Timetable getTimetable(Long timetableId) {
        return em.createQuery("select t from Timetable t where t.id = :id", Timetable.class)
                .setParameter("id", timetableId)
                .getSingleResult();
    }

    @Override
    public Timetable getTimetable(int year, int week) {
        return em.createQuery("select t from Timetable t where t.year = :y and t.week = :w", Timetable.class)
                .setParameter("y", year)
                .setParameter("w", week)
                .getSingleResult();
    }

    @Override
    public List<TimetableEntry> getAllTimetableEntries(Long timetableId) {
        return em.createQuery("select t from Timetable t where t.id = :id", Timetable.class)
                .setParameter("id", timetableId)
                .getSingleResult()
                .getEntries();
    }
}
