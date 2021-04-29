package cz.idomatojde;

import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class providing predefined Entity objects
 *
 * @author Michal Hazdra
 */
public final class TestObjects {

    public static User getUser(String username, String password) {
        User user = new User();
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setName("Name");
        user.setSurname("Surname");
        user.setPhoneNumber("+420123456789");
        user.setEmail(username + "@mail.com");
        user.setCredits(123);
        user.setWantsAdvertisement(false);
        user.setAdmin(false);

        return user;
    }

    public static User getUser(String username) {
        return getUser(username, "password");
    }

    public static Timetable getTimetable(User user, int year, int week) {
        Timetable timetable = new Timetable();

        timetable.setUser(user);
        timetable.setYear(year);
        timetable.setWeek(week);

        return timetable;
    }

    public static TimetableEntry getTimetableEntry(Timetable timetable, int day, LocalTime start, Duration length) {
        TimetableEntry timetableEntry = new TimetableEntry();

        timetableEntry.setTimetable(timetable);
        timetableEntry.setDay(day);
        timetableEntry.setEntryStart(start);
        timetableEntry.setLength(length);

        return timetableEntry;
    }

    public static Offer getOffer(User user, String title) {
        Offer offer = new Offer();

        offer.setTitle(title);
        offer.setOwner(user);
        offer.setDescription("description");
        offer.setCategory(Category.EDUCATION);
        offer.setCapacity(10);
        offer.setRegistered(5);
        offer.setPrice(BigDecimal.ONE);
        offer.setCreatedDate(LocalDate.of(2021, 4, 1));
        offer.setExpirationDate(LocalDate.now().plusDays(1));

        return offer;
    }
}
